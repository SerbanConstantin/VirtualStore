package db.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    List<T> findAll();

   T save(T o); //insert and update all together

    T deleteById(K id);
   Optional<T> findById(K id);

}
