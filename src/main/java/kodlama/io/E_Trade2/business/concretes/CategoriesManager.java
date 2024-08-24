package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.CategoriesService;
import kodlama.io.E_Trade2.business.rules.CategoryBusinessRules;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CategoriesRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCategoriesResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCategoriesResponse;
import kodlama.io.E_Trade2.entities.concretes.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesManager implements CategoriesService {

    private CategoriesRepository categoriesRepository;
    private ModelMapperService modelMapperService;
    private CategoryBusinessRules categoryBusinessRules;
    @Override
    public Set<GetAllCategoriesResponse> getAll() {
        List<Category> categories = this.categoriesRepository.findAll();

        Set<GetAllCategoriesResponse> getAllCategoriesResponses = categories.stream()
                .map(category -> this.modelMapperService.forResponse()
                        .map(category,GetAllCategoriesResponse.class)).collect(Collectors.toSet());
        return getAllCategoriesResponses;
    }

    @Override
    public GetByIdCategoriesResponse getById(Long id) {
        Category category = this.categoriesRepository.findById(id).orElseThrow();
        GetByIdCategoriesResponse getByIdCategoriesResponse = this.modelMapperService.forResponse()
                .map(category,GetByIdCategoriesResponse.class);
        return getByIdCategoriesResponse;
    }

    @Override
    public void add(CreateCategoriesRequest createCategoriesRequest) {

        this.categoryBusinessRules.checkIfCategoryNameExists(createCategoriesRequest.getName());

        Category category = this.modelMapperService.forRequest().map(createCategoriesRequest,Category.class);
        this.categoriesRepository.save(category);

    }

    @Override
    public void update(UpdateCategoriesRequest updateCategoriesRequest) {

        Category category = this.modelMapperService.forRequest().map(updateCategoriesRequest,Category.class);
        this.categoriesRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        this.categoriesRepository.deleteById(id);

    }
}
