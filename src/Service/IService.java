package Service;

import java.util.List;

/**
 * Created by manozct on 6/18/2017.
 */
public interface IService<T> {
    Object insert(T t);
    Object update(T t, String id);
    T get(String id);
    List<T> getAll();
}
