package com.tass.productservice.model.response;


import com.tass.productservice.model.BasePagingData;
import com.tass.productservice.model.BaseResponse;

import com.tass.productservice.model.dto.CategoryInfo;
import lombok.Data;

import java.util.List;

@Data
public class SearchCategoryResponse extends BaseResponse {
    private Data data;
    public SearchCategoryResponse(){
        super();
    }
    @lombok.Data
    public static class Data extends BasePagingData {

        private List<CategoryInfo> items;
    }
}
