package com.singular.coffedelivery.domain.repository;

import com.singular.coffedelivery.domain.entity.FileEntity;
import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByProduto(ProdutoEntity produto);
    FileEntity findFileEntitiesByProduto(ProdutoEntity produto);


}
