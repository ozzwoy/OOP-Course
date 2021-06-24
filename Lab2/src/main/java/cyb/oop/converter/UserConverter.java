package cyb.oop.converter;

import cyb.oop.dto.UserDTO;
import cyb.oop.entity.User;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public UserDTO toDTO(User entity) {
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setRole(entity.getRole());

        return dto;
    }

    public User toEntity(UserDTO dto){
        User entity = new User();

        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());

        return entity;
    }

    public List<UserDTO> listToDTO(List<User> userList) {
        List<UserDTO> result = new ArrayList<>();

        for (User user : userList) {
            result.add(toDTO(user));
        }

        return result;
    }
}
