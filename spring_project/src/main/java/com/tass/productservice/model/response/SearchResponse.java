package com.tass.productservice.model.response;

import com.tass.productservice.model.BasePagingData;
import com.tass.productservice.model.BaseResponse;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse extends BaseResponse {
    private Item item;
    public SearchResponse(){
        super();
    }

    public SearchResponse(int code, String messsage, Item item) {
        super(code, messsage);
        this.item = item;
    }

    @Data
    public static class Item extends BasePagingData {
        private List<?> items;
    }
}