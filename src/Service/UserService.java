package Service;

import Model.User;
import com.asd.framework.DatabaseConnection.Db.DbAccess;

import java.util.HashMap;
import java.util.List;

/**
 * Created by manozct on 6/18/2017.
 */
public class UserService implements IService<User> {
    @Override
    public User insert(User user) {
        HashMap<String, String> hmpUser = new HashMap<>();
        hmpUser.put("Name", user.getName());
        hmpUser.put("Address", user.getAddress());
        hmpUser.put("DOB", String.valueOf(user.getDOB()));
        hmpUser.put("Contact", user.getContact());
        hmpUser.put("Email", user.getEmail());
        hmpUser.put("Password", user.getPassword());
        hmpUser.put("Category", user.getCategory());
        hmpUser.put("Role", user.getRole());
        DbAccess.table("user")
                .values(hmpUser).insert();
        return user;
    }

    @Override
    public User update(User user, String id) {
        return null;
    }

    @Override
    public User get(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
