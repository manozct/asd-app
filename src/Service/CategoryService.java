package Service;

import Model.Category;
import com.asd.framework.DatabaseConnection.Db.DbAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manozct on 6/18/2017.
 */
public class CategoryService implements IService<Category> {
    @Override
    public Object insert(Category category) {
        return null;
    }

    @Override
    public Object update(Category category, String id) {
        return null;
    }

    @Override
    public Category get(String id) {
        return null;
    }

    @Override
    public List<Category> getAll() {
        List<Category> lstCategory = new ArrayList<>();

        ResultSet rs = DbAccess.table("Category").get();
        try {
            while (rs.next()) {
                Category category = new Category();

                category.setCategoryId(Integer.valueOf(rs.getString(1)));
                category.setCategoryName(rs.getString(2));
                lstCategory.add(category);
                //System.out.println("list:"+lstCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // System.out.println(rs);
       // System.out.println("list:" + lstCategory);
        return lstCategory;
    }
}
