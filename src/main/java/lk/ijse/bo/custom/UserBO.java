package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.util.List;

public interface UserBO extends SuperBO {

    String getCurrentId();

    List<UserDTO> getAllUser();

    boolean save(UserDTO userDTO);

    List<UserDTO> getAll();

    String getUserRole(String username);

    boolean checkCredentials(String username, String password);

    boolean update(UserDTO userDTO);

    boolean delete(String userId);

    UserDTO searchByID(String userId);
}
