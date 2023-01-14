package com.tass.userservice.services;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.userservice.repositories.RoleRepository;
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

    public BaseResponseV2 findAll() throws ApplicationException {
        return new BaseResponseV2(roleRepository.findAll());
    }
}
