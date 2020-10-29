package com.ecart.shop.repository;

import com.ecart.shop.entity.InventoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends CrudRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByProduct_ProductId(Long productId);
}
