package com.tass.productservice.databases.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "category_relationship")
@Data
public class CategoryRelationship {
    @EmbeddedId
    private PK pk;

    @Data
    @Embeddable
    public static class PK implements Serializable {
        @Column(name = "id")
        private long parentId;
        @Column(name = "link_id")
        private long childrenId;

        public PK(long parentId, long childrenId) {
            this.parentId = parentId;
            this.childrenId = childrenId;
        }

        public PK() {

        }
    }
}
