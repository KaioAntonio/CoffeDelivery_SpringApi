package com.singular.coffedelivery.repository;

import com.singular.coffedelivery.entity.FileEntity;
import com.singular.coffedelivery.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByProduto(ProdutoEntity produto);
    FileEntity findFileEntitiesByProduto(ProdutoEntity produto);


}
