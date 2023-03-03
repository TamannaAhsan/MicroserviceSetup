package com.tamanna.customerserver.service.roles;

import com.tamanna.customerserver.dto.roles.RoleDTO;
import com.tamanna.customerserver.exception.ApiSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleDTO create (RoleDTO rolesDTO) throws ApiSystemException;

    List<RoleDTO> getAll ();

    RoleDTO getById (Long id) throws ApiSystemException;
}
