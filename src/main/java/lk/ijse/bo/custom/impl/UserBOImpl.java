package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.Users);

    @Override
    public String getCurrentId() {
        return userDAO.getCurrentId();
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users =  userDAO.getAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO(user.getUserId(),user.getUsername(),user.getPassword(), user.getEmail(),user.getTel(),user.getRole());
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Override
    public boolean save(UserDTO userDTO) {
       return userDAO.save(new User(userDTO.getUserId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getTel(), userDTO.getRole()));
    }

    @Override
    public List<UserDTO> getAll() {
        List<UserDTO> userDto = new ArrayList<>();
        List<User> all = userDAO.getAll();
        for (User user : all){
            userDto.add(new UserDTO(user.getUserId(),user.getUsername(),user.getPassword(),user.getEmail(),user.getTel(),user.getRole()));
        }
        return userDto;
    }

    @Override
    public String getUserRole(String username) {
        return userDAO.getUserRole(username);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        return userDAO.checkCredentials(username,  password);
    }
}
