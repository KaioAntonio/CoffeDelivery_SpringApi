package com.singular.coffedelivery.repository;


import com.singular.coffedelivery.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
}
