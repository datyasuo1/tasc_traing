package com.tass.productservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.tass.productservice.databases.entities.Category;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryInfo {
    private BigInteger id;
    private String name;
    @Lob
    private String description;
    private String icon;
    private Date created_at;
    private Date updated_at;
    private int is_root;
    @Lob
    private Set<Category> child;

    @Lob
    private Set<Category> parent;



//    public CategoryInfo(Long id, String name, String icon, String description, Integer isRoot){
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.icon =  icon;
//        this.isRoot = isRoot;
//    }

    public void setChild(String child) {
        Gson gson = new Gson();
        Set<Category> childSet = gson.fromJson(child, Set.class);
        this.child = childSet;
    }

    public void setParent(String parent) {
        Gson gson = new Gson();
        Set<Category> parentSet = gson.fromJson(parent, Set.class);
        this.parent = parentSet;
    }

}
