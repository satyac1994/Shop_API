package com.ecart.shop.entity;

import lombok.Data;

import javax.persistence.*;

import com.ecart.shop.model.Status;

@Entity(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Integer quantity;
    @OneToOne
    @JoinColumn(name = "productId")//product_id
    private ProductEntity product;

    @Enumerated(EnumType.STRING )
    @Column(length = 32, columnDefinition = "varchar(32) default 'CREATED'")
    private Status status;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
    
    
    
   
}
