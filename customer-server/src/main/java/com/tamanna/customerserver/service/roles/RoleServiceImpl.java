package com.tamanna.customerserver.service.roles;

import com.tamanna.customerserver.dto.mapper.AutoUserMapper;
import com.tamanna.customerserver.dto.roles.RoleDTO;
import com.tamanna.customerserver.entity.roles.Role;
import com.tamanna.customerserver.exception.ApiSystemException;
import com.tamanna.customerserver.repository.roles.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    @Override
    public RoleDTO create(RoleDTO rolesDTO) throws ApiSystemException {
        try{
            Role role = AutoUserMapper.MAPPER.mapToRole(rolesDTO);
            role.setRoleName(rolesDTO.getRoleName());
            Role savedRole = roleRepository.save(role);
            RoleDTO savedRoleDTO = AutoUserMapper.MAPPER.mapToRoleDTO(savedRole);
            return savedRoleDTO;

        }catch (Exception e) {
            throw new ApiSystemException("role.not.created");
        }
    }
    @Override
    public List<RoleDTO> getAll() {
       List<Role> roles = roleRepository.findAll();
       return roles.stream()
               .map(element-> AutoUserMapper.MAPPER.mapToRoleDTO(element))
               .collect(Collectors.toList());
    }
    @Override
    public RoleDTO getById(Long id) throws ApiSystemException {
        try{
            Role role = roleRepository.findById(id)
                    .orElseThrow(()->new ApiSystemException("Role is not found"));
            RoleDTO roleDTO = AutoUserMapper.MAPPER.mapToRoleDTO(role);
            return roleDTO;
        }catch (Exception e) {
            throw new ApiSystemException("role.not.created");
        }
    }
}
