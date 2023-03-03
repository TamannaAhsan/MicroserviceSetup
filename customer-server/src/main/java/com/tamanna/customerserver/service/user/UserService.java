package com.tamanna.customerserver.service.user;

import com.tamanna.customerserver.dto.user.UserDTO;
import com.tamanna.customerserver.dto.user.UserLoginDTO;
import com.tamanna.customerserver.exception.ApiSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDTO create (UserDTO userDTO) throws ApiSystemException;
    List<UserDTO> getAll ();
    UserDTO getById (Long id) throws ApiSystemException;
    UserDTO resetPassword (Long id, String password) throws ApiSystemException;
    UserDTO forgotPassword () throws ApiSystemException;
    UserDTO getUserByName (String userName) throws ApiSystemException;
    UserLoginDTO isUserNamePasswordValid(UserLoginDTO userLoginDTO)throws ApiSystemException;
}
