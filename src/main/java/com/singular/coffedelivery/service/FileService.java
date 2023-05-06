package com.singular.coffedelivery.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.dto.produto.FileDTO;
import com.singular.coffedelivery.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.entity.FileEntity;
import com.singular.coffedelivery.entity.ProdutoEntity;
import com.singular.coffedelivery.exception.RegraDeNegocioException;
import com.singular.coffedelivery.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final ProdutoService produtoService;

    private final ObjectMapper objectMapper;

    public FileDTO store(MultipartFile file, Integer id) throws RegraDeNegocioException {
        try {
            ProdutoDTO produto = produtoService.findById(id);
            ProdutoEntity produtoEntity = objectMapper.convertValue(produto, ProdutoEntity.class);
            FileEntity fileEntityExiste = fileRepository.findFileEntitiesByProduto(produtoEntity);
            FileEntity fileDB = new FileEntity();
            if (fileEntityExiste != null) {
                fileDB = fileEntityExiste;
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileDB.setName(fileName);
            fileDB.setType(file.getContentType());
            fileDB.setData(file.getBytes());

            fileDB.setProduto(produtoEntity);
            FileEntity fileEntity = fileRepository.save(fileDB);
            FileDTO fileDTO = objectMapper.convertValue(fileEntity, FileDTO.class);
            return fileDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao enviar a imagem!");
        }
    }

    public String getImage(Integer id) throws RegraDeNegocioException {
        ProdutoDTO produto = produtoService.findById(id);
        ProdutoEntity produtoEntity = objectMapper.convertValue(produto, ProdutoEntity.class);
        FileEntity fileEntity = fileRepository.findByProduto(produtoEntity)
                .orElseThrow(() -> new RegraDeNegocioException("Imagem não encontrada ou não existe."));
        return Base64Utils.encodeToString(fileEntity.getData());

    }

}