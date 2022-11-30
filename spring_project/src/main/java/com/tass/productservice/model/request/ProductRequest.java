package com.tass.productservice.model.request;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "products")
public class ProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String barcode;
    private String name;
    private String image;
    private String description;
    private String content;

}
