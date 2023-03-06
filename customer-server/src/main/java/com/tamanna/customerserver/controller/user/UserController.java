package com.tamanna.customerserver.controller.user;

import com.tamanna.customerserver.dto.user.UserDTO;
import com.tamanna.customerserver.dto.user.UserLoginDTO;
import com.tamanna.customerserver.exception.ApiSystemException;
import com.tamanna.customerserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDTO> register (@RequestBody UserDTO userDTO) throws ApiSystemException {
        UserDTO savedUserDTO = userService.create(userDTO);
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/check-login")
    public ResponseEntity <UserLoginDTO> isUserNamePasswordValid(@RequestBody UserLoginDTO userLoginDTO) throws ApiSystemException {
        UserLoginDTO userLogin = userService.isUserNamePasswordValid(userLoginDTO);
        return new ResponseEntity<>(userLogin, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<UserDTO> getById (@PathVariable Long id) throws ApiSystemException {
        UserDTO userDTO = userService.getById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getByUserName (@PathVariable String username) throws ApiSystemException {
        UserDTO userDTO = userService.getUserByName(username);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAll (){
        List<UserDTO> userDTOS = userService.getAll();
        return new ResponseEntity<>(userDTOS,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> resetPassword (@PathVariable Long id, @RequestBody UserLoginDTO password) throws ApiSystemException {
        UserDTO userDTO = userService.resetPassword(id, password.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
