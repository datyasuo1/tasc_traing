package com.example.smart_shop.service;

import com.example.smart_shop.databases.entities.Product;
import com.example.smart_shop.databases.entities.Role;
import com.example.smart_shop.databases.entities.User;
import com.example.smart_shop.databases.myenum.UserStatus;
import com.example.smart_shop.databases.repository.RoleRepository;
import com.example.smart_shop.databases.repository.UserRepository;
import com.example.smart_shop.model.request.CredentialRequest;
import com.example.smart_shop.model.request.LoginRequest;
import com.example.smart_shop.model.request.RegisterRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.model.response.ERROR;
import com.example.smart_shop.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    public boolean matchPassword(String rawPassword, String hashPassword){
        return passwordEncoder.matches(rawPassword,hashPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE);
        User user = userOptional.orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
    public CredentialRequest generateCredential(UserDetails userDetail, HttpServletRequest request) {
        String accessToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JWTUtil.ONE_DAY * 7);

        String refreshToken = JWTUtil.generateToken(userDetail.getUsername(),
                userDetail.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURI(),
                JWTUtil.ONE_DAY * 14);
        return new CredentialRequest(accessToken, refreshToken);
    }

    public BaseResponse register(RegisterRequest registerRequest){
        validateRequestCreateException(registerRequest);
        if (!registerRequest.getPassword().equals(registerRequest.getRePass())){
            return new BaseResponse<>(100,"Repair Password not match");
        }
        Role role = roleRepository.findByName("USER");
        User user = User.builder()
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .fullName(registerRequest.getFullName())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .avatar("https://as1.ftcdn.net/v2/jpg/03/53/11/00/1000_F_353110097_nbpmfn9iHlxef4EDIhXB1tdTD0lcWhG9.jpg?fbclid=IwAR0IeeX4fdIKXrmKyVLdn3mGEhAkNFdQv3MH7f4P5okIBtG_Rx_fqonZjss")
                .role(role)
                .status(UserStatus.ACTIVE)
                .build();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return new BaseResponse<>(200,"success");
    }

    public BaseResponse login(LoginRequest loginRequest,HttpServletRequest request){
        if (loginRequest.getUsername() == null) {
            return new BaseResponse<>(100,"Username missing") ;
        }
        if (loginRequest.getPassword() == null) {
            return new BaseResponse<>(100,"Password missing") ;
        }
        UserDetails userDetail = loadUserByUsername(loginRequest.getUsername());
        if (userDetail == null) {
            return new BaseResponse<>(100,"User not found") ;        }
        if (matchPassword(loginRequest.getPassword(), userDetail.getPassword())) {
            CredentialRequest credentialRequest = generateCredential(userDetail, request);
            return new BaseResponse<>(200,"Login Success",credentialRequest) ;
        }
        return new BaseResponse<>(400,"Login fails") ;
    }

    private void validateRequestCreateException(RegisterRequest request) throws ApiException {

        if (StringUtils.isBlank(request.getEmail())) {
            log.debug("icon is blank");
            throw new ApiException(ERROR.INVALID_PARAM, "email is blank");
        }

        if (StringUtils.isBlank(request.getFullName())) {
            throw new ApiException(ERROR.INVALID_PARAM, "name is blank");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApiException(ERROR.INVALID_PARAM, "description is Blank");
        }
        if (StringUtils.isBlank(request.getUsername())) {
            throw new ApiException(ERROR.INVALID_PARAM, "username is Blank");
        }
    }

    public BaseResponse findById(Long id) throws ApiException{
        return new BaseResponse<>(200,"Success",userRepository.findById(id));
    }

    public Page<User> findAllAndSearch(Specification<User> specification, int page, int limit) {
        return userRepository.findAll(specification, PageRequest.of(page, limit, Sort.by("updatedAt").descending()));
    }
    public BaseResponse blockUser(Long id) throws ApiException{
        Optional<User> optionalUser = userRepository.findByIdAndStatus(id,UserStatus.ACTIVE);
        if (!optionalUser.isPresent()){
            return  new BaseResponse<>(100,"User not found");
        }
        User user = optionalUser.get();
        user.setStatus(UserStatus.DEACTIVE);
        return new BaseResponse<>(200,"Success");
    }
    public BaseResponse unlockUser(Long id) throws ApiException{
        Optional<User> optionalUser = userRepository.findByIdAndStatus(id,UserStatus.DEACTIVE);
        if (!optionalUser.isPresent()){
            return  new BaseResponse<>(100,"User not found");
        }
        User user = optionalUser.get();
        user.setStatus(UserStatus.ACTIVE);
        return new BaseResponse<>(200,"Success");
    }

    public Optional<User> findByNameActive(String username) {
        return userRepository.findByUsernameAndStatus(username, UserStatus.ACTIVE);
    }
}
