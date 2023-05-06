package com.singular.coffedelivery.security;

import com.singular.coffedelivery.entity.CargoEntity;
import com.singular.coffedelivery.entity.UsuarioEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String KEY_CARGOS = "CARGOS";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;

    public String getToken(UsuarioEntity usuarioEntity) {
        LocalDateTime now = LocalDateTime.now();
        Date nowDT = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date expDT = Date.from(now.plusDays(Long.parseLong(expiration)).atZone(ZoneId.systemDefault()).toInstant());

        List<String> cargosUsuarios = usuarioEntity.getCargos()
                .stream()
                .map(CargoEntity::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setIssuer("scanner-api")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .claim(KEY_CARGOS, cargosUsuarios)
                .setIssuedAt(nowDT)
                .setExpiration(expDT)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token != null) {
            token = token.replace("Bearer ", "");

            Claims chaves = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String idUsuario = chaves.get(Claims.ID, String.class);
            List<String> cargos = chaves.get(KEY_CARGOS, List.class);

            List<SimpleGrantedAuthority> listaDeCargos = cargos.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken userDTO = new UsernamePasswordAuthenticationToken(
                    idUsuario,
                    null,
                    listaDeCargos
            );

            return userDTO;
        }
        return null;
    }
}
