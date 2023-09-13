package com.singular.coffedelivery.domain.repository;

import com.singular.coffedelivery.domain.entity.PedidoEntity;
import com.singular.coffedelivery.util.enums.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Integer> {

    List<PedidoEntity> findBySituacao(Situacao situacao);

}
