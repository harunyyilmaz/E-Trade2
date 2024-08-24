package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.CategoriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryBusinessRules {
    private CategoriesRepository categoriesRepository;

    public void checkIfCategoryNameExists(String name){
        if (this.categoriesRepository.existsByName(name)){
            throw  new BusinessException("Category name already exists");
        }
    }
}
