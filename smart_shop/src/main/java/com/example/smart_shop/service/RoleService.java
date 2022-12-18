package com.example.smart_shop.service;

import com.example.smart_shop.databases.repository.RoleRepository;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public BaseResponse findAll() throws ApiException{
        return new BaseResponse<>(200,"Success",roleRepository.findAll());
    }
}
