package com.singular.coffedelivery.domain.repository;

import com.singular.coffedelivery.domain.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer> {


}
