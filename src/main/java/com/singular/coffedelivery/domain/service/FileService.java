package com.singular.coffedelivery.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singular.coffedelivery.domain.entity.FileEntity;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import com.singular.coffedelivery.domain.dto.produto.FileDTO;
import com.singular.coffedelivery.domain.dto.produto.ProdutoDTO;
import com.singular.coffedelivery.config.exception.RegraDeNegocioException;
import com.singular.coffedelivery.domain.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final ProdutoService produtoService;

    private final ObjectMapper objectMapper;

    public FileDTO store(MultipartFile file, Integer id) throws RegraDeNegocioException {
        try {
            ProdutoDTO produto = produtoService.buscarPorId(id);
            ProdutoEntity produtoEntity = objectMapper.convertValue(produto, ProdutoEntity.class);
            FileEntity fileEntityExiste = fileRepository.findFileEntitiesByProduto(produtoEntity);
            FileEntity fileDB = new FileEntity();
            if (fileEntityExiste != null) {
                fileDB = fileEntityExiste;
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileDB.setName(fileName);
            fileDB.setType(file.getContentType());
            fileDB.setImagem(file.getBytes());

            fileDB.setProduto(produtoEntity);
            FileEntity fileEntity = fileRepository.save(fileDB);
            FileDTO fileDTO = objectMapper.convertValue(fileEntity, FileDTO.class);
            fileDTO.setNome(produto.getNome());
            fileDTO.setDescricao(produto.getDescricao());
            fileDTO.setPreco(produto.getPreco());
            fileDTO.setTipo(produto.getTipo());
            fileDTO.setIdProduto(produto.getIdProduto());
            fileDTO.setSituacao(produto.getSituacao());
            fileDTO.setDtCriacao(produto.getDtCriacao());
            fileDTO.setQtProduto(produto.getQtProduto());
            return fileDTO;
        } catch (Exception e) {
            produtoService.delete(id);
            throw new RegraDeNegocioException("Ocorreu um erro ao enviar a imagem!");
        }
    }

    public String getImage(Integer id) throws RegraDeNegocioException {
        ProdutoDTO produto = produtoService.buscarPorId(id);
        ProdutoEntity produtoEntity = objectMapper.convertValue(produto, ProdutoEntity.class);
        FileEntity fileEntity = fileRepository.findByProduto(produtoEntity)
                .orElseThrow(() -> new RegraDeNegocioException("Imagem não encontrada ou não existe."));
        return Base64Utils.encodeToString(fileEntity.getImagem());

    }

    public List<ProdutoDTO> getProductsAllImages() throws RegraDeNegocioException {
        List<ProdutoDTO> produtoDTOList = produtoService.buscar();
        for (ProdutoDTO p:
             produtoDTOList) {
            String imagem = getImage(p.getIdProduto());
            p.setImagem(imagem);
        }
        return produtoDTOList;
    }

}