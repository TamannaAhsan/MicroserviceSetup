package com.tamanna.customerserver.service.user;

import com.tamanna.customerserver.dto.mapper.AutoUserMapper;
import com.tamanna.customerserver.dto.user.UserDTO;
import com.tamanna.customerserver.dto.user.UserLoginDTO;
import com.tamanna.customerserver.entity.roles.Role;
import com.tamanna.customerserver.entity.user.User;
import com.tamanna.customerserver.exception.ApiNotAcceptableException;
import com.tamanna.customerserver.exception.ApiSystemException;
import com.tamanna.customerserver.repository.roles.RoleRepository;
import com.tamanna.customerserver.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.tamanna.customerserver.utils.Utility.isNotNull;

@AllArgsConstructor
@Slf4j
@Component
public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;
    @Override
    public UserDTO create(UserDTO userDTO) throws ApiSystemException {
        try{
            logger.info("Save User Information");
            if ((userRepository.findByUsernameIgnoreCase(userDTO.getUsername())).isPresent()) {
                throw new ApiNotAcceptableException("username.already.exist");
            }

            Role role = roleRepository.findById(userDTO.getRole().getId())
                    .orElseThrow(()->new ApiSystemException("Role not found"));

            userDTO.setCreatedAt(LocalDateTime.now());
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User user = AutoUserMapper.MAPPER.mapToUser(userDTO);
            user.setRole(role);
            User savedUser = userRepository.save(user);
            UserDTO savedUserDTO = AutoUserMapper.MAPPER.mapToUserDTO(savedUser);
            return savedUserDTO;

        }catch (Exception e) {
            throw new ApiSystemException("user.not.created");
        }
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(element-> AutoUserMapper.MAPPER.mapToUserDTO(element))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Long id) throws ApiSystemException {
        try{
            User user = userRepository.findById(id)
                    .orElseThrow(()->new ApiSystemException("User is not found"));
            UserDTO userDTO = AutoUserMapper.MAPPER.mapToUserDTO(user);
            return userDTO;
        }catch (Exception e) {
            throw new ApiSystemException("user.not.found");
        }
    }

    @Override
    public UserDTO resetPassword(Long id, String password) throws ApiSystemException {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(()->new ApiSystemException("User is not found"));
            existingUser.setPassword(password);
            UserDTO userDTO = AutoUserMapper.MAPPER.mapToUserDTO(existingUser);
            logger.info("Update Password Information");
            return userDTO;
        }catch (Exception e) {
            throw new ApiSystemException("user.password.not.updated");
        }
    }

    @Override
    public UserDTO getUserByName(String username) throws ApiSystemException {
        try{
            User user = userRepository.findByUsernameIgnoreCase(username)
                    .orElseThrow(()->new ApiSystemException("User is not found"));
            UserDTO userDTO = AutoUserMapper.MAPPER.mapToUserDTO(user);
            return userDTO;

        }catch (Exception e) {
            throw new ApiSystemException("user.not.found");
        }
    }

    @Override
    public UserLoginDTO isUserNamePasswordValid(UserLoginDTO userLoginDTO) throws ApiSystemException {
        try{
            log.info("trying to validate username: " + userLoginDTO.getUserName());
            User savedUser = userRepository.findByUsernameIgnoreCase(userLoginDTO.getUserName())
                    .orElseThrow(()-> new ApiNotAcceptableException("user not found with username: "+ userLoginDTO.getUserName()));

            if (passwordEncoder.matches(userLoginDTO.getPassword(), savedUser.getPassword())){
                Role role = savedUser.getRole();
                if(isNotNull(role))
                    userLoginDTO.setRole(role.getRoleName());
                return userLoginDTO;
            }
            return null;
        } catch (Exception e) {
            throw new ApiSystemException("user.name.password.check.failed");
        }
    }

    @Override
    public UserDTO forgotPassword() throws ApiSystemException {
        return null;
    }

}
