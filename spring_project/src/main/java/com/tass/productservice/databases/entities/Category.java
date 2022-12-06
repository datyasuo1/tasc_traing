package com.tass.productservice.databases.entities;

import com.tass.productservice.utils.Constant;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String icon;

    private int isRoot;
    public boolean checkIsRoot(){
        return isRoot == Constant.ONOFF.ON;
    }

}
