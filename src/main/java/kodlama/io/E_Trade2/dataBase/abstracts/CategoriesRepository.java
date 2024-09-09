package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Category,Long> {

    boolean existsByName(String name);

    Optional<Category> findByName(String name);
}
