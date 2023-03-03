package com.tamanna.customerserver.controller.roles;

import com.tamanna.customerserver.dto.roles.RoleDTO;
import com.tamanna.customerserver.exception.ApiSystemException;
import com.tamanna.customerserver.service.roles.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService rolesService;

    @PostMapping("/")
    public ResponseEntity<RoleDTO> create (@RequestBody RoleDTO roleDTO) throws ApiSystemException {
        RoleDTO savedRoleDTO = rolesService.create(roleDTO);
        return new ResponseEntity<>(savedRoleDTO, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<RoleDTO>> getAll (){
        List<RoleDTO> roleDTO = rolesService.getAll();
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getById (@PathVariable Long id) throws ApiSystemException {
        RoleDTO roleDTO = rolesService.getById(id);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

}
