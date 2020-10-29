package com.ecart.shop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "inventory")
    private ProductEntity product;

    private Integer quantity;

    private Integer reservedInventory;

    private Integer settledInventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReservedInventory() {
        return reservedInventory;
    }

    public void setReservedInventory(Integer reservedInventory) {
        this.reservedInventory = reservedInventory;
    }

    public Integer getSettledInventory() {
        return settledInventory;
    }

    public void setSettledInventory(Integer settledInventory) {
        this.settledInventory = settledInventory;
    }
}
