package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.dao.SuperDAO;
import lk.ijse.entity.User;

import java.util.List;

public interface UserDAO extends CrudDAO {

    String getCurrentId();

    List<User> getAll();

    String getUserRole(String username);

    boolean checkCredentials(String username, String password);

    boolean update(User user);

    boolean delete(String userId);

    User searchUserId(String userId);
}
