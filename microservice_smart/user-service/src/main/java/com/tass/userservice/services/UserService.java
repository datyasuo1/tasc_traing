package com.tass.userservice.services;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.ERROR;
import com.tass.common.model.myenums.UserStatus;
import com.tass.common.redis.dto.UserLoginDTO;
import com.tass.common.redis.repository.UserLoginRepository;
import com.tass.userservice.entities.Role;
import com.tass.userservice.entities.User;

import com.tass.userservice.repositories.RoleRepository;
import com.tass.userservice.repositories.UserRepository;
import com.tass.userservice.request.LoginRequest;
import com.tass.userservice.request.UserRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    public BaseResponseV2 register(UserRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getUsername())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username is empty");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password is empty");
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }
        Role role = roleRepository.findByName("ADMIN");
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setAvatar("https://as1.ftcdn.net/v2/jpg/03/53/11/00/1000_F_353110097_nbpmfn9iHlxef4EDIhXB1tdTD0lcWhG9.jpg?fbclid=IwAR0IeeX4fdIKXrmKyVLdn3mGEhAkNFdQv3MH7f4P5okIBtG_Rx_fqonZjss");
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new BaseResponseV2<>(user);
    }

    public BaseResponseV2<UserLoginDTO> login(LoginRequest request) throws ApplicationException {
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(request.getUsername(),UserStatus.ACTIVE);

        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username not found");
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        String token = UUID.randomUUID().toString();

        userLoginDTO.setToken(token);
        userLoginDTO.setUserId(user.getId());
        userLoginDTO.setTimeToLive(100000);
        userLoginDTO.setRole(user.getRole().getName());
        userLoginRepository.save(userLoginDTO);

        BaseResponseV2<UserLoginDTO> loginResponse = new BaseResponseV2<>();
        loginResponse.setData(userLoginDTO);

        return loginResponse;
    }

    public BaseResponseV2 findById(Long id) throws ApplicationException{
        return new BaseResponseV2(userRepository.findById(id));
    }

    public Page<User> findAllAndSearch(Specification<User> specification, int page, int limit) {
        return userRepository.findAll(specification, PageRequest.of(page, limit, Sort.by("updatedAt").descending()));
    }
    public BaseResponseV2 blockUser(Long id) throws ApplicationException{
        Optional<User> optionalUser = userRepository.findByIdAndStatus(id,UserStatus.ACTIVE);
        if (!optionalUser.isPresent()){
            throw new ApplicationException(ERROR.ID_NOT_FOUND,"User not found");
        }
        User user = optionalUser.get();
        user.setStatus(UserStatus.BLOCKED);
        return new BaseResponseV2();
    }
    public BaseResponseV2 unlockUser(Long id) throws ApplicationException{
        Optional<User> optionalUser = userRepository.findByIdAndStatus(id,UserStatus.BLOCKED);
        if (!optionalUser.isPresent()){
            throw new ApplicationException(ERROR.ID_NOT_FOUND,"User not found");
        }
        User user = optionalUser.get();
        user.setStatus(UserStatus.ACTIVE);
        return new BaseResponseV2();

    }

    public Optional<User> findByNameActive(String username) {
        return userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE);
    }
}
