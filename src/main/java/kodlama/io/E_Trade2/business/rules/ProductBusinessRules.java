package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductBusinessRules {
    private ProductsRepository productsRepository;

    public void checkIfProductNameExists(String name){
        if (this.productsRepository.existsByName(name)){
            throw new BusinessException("Product name already exists");
        }
    }

    public List<Product> getProductsInPriceRange(double minPrice , double maxPrice){
        if(minPrice>maxPrice){
            throw new BusinessException("Minimum price cannot be greater than maximum price.");
        }
        return this.productsRepository.findByPriceBetween(minPrice,maxPrice);
    }
}
