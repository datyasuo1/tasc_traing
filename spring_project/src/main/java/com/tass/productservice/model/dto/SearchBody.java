package com.tass.productservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private int page;
    private int limit;
    private String name;
    private String icon;
    private String description;
    private String start;
    private String end;
    private int isRoot;
    private String sort;
    private Long id;

    public static final class SearchBodyBuilder {
        private String name;
        private String icon;
        private String description;
        private int isRoot;

        private SearchBodyBuilder() {
        }

        public static SearchBodyBuilder aSearchBody() {
            return new SearchBodyBuilder();
        }

        public SearchBodyBuilder withIsRoot(int isRoot) {
            this.isRoot = isRoot;
            return this;
        }

        public SearchBodyBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SearchBodyBuilder withIcon(String icon) {
            this.icon = icon;
            return this;
        }

        public SearchBodyBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SearchBody build() {
            SearchBody searchBody = new SearchBody();
            searchBody.setDescription(description);
            searchBody.setIcon(icon);
            searchBody.setIsRoot(isRoot);
            searchBody.setName(name);
            return searchBody;
        }
    }
}
