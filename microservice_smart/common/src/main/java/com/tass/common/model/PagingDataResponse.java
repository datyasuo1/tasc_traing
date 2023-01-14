package com.tass.common.model;

import lombok.Data;

@Data
public class PagingDataResponse {
    private long total;

    private int totalPage;

    private int currentPage;


    private int pageSize;
}
