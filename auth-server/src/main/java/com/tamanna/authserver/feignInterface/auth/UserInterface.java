package com.tamanna.authserver.feignInterface.auth;

import com.tamanna.authserver.response.user.UserLoginDTO;
import com.tamanna.authserver.response.user.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "CUSTOMER-SERVICE", path = "api/v1")
@Service
public interface UserInterface {

    @GetMapping(value = "user/username/{username}")
    Optional<UserResponseDTO> fetchUserByUsername(@PathVariable("username") String username);

    @PostMapping(value = "/user/check-login")
    UserLoginDTO isUserNamePasswordValid(@RequestBody UserLoginDTO userLoginDTO);
}
