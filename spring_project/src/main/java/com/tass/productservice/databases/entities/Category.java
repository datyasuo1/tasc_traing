package com.tass.productservice.databases.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tass.productservice.utils.Constant;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    private String icon;

//    @JsonProperty("is_root")
    private int isRoot;
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "category_relationship",
//            joinColumns = @JoinColumn(name = "parent_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id")
//    )
//    @JsonBackReference
//    private Set<Category> categories;
//    @ManyToMany(mappedBy = "categories")
//    @JsonBackReference
//    private Set<Category> parentCategories;

    public boolean checkIsRoot(){
        return isRoot == Constant.ONOFF.ON;
    }

}
