package com.example.smart_shop.databases.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.org.glassfish.gmbal.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "color")
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    private int qty;
//    @ManyToOne
//    private Product product;
}
