package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.CategoriesService;
import kodlama.io.E_Trade2.dtos.requests.CreateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCategoriesResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCategoriesResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {

    private CategoriesService categoriesService;

    @GetMapping
    public Set<GetAllCategoriesResponse> getAll(){
        return this.categoriesService.getAll();
    }
    @GetMapping("/{id}")
    public GetByIdCategoriesResponse getById(@PathVariable Long id){
        return this.categoriesService.getById(id);
    }
    @PostMapping
    public void add(@RequestBody @Valid CreateCategoriesRequest createCategoriesRequest){
        this.categoriesService.add(createCategoriesRequest);
    }
    @PutMapping
    public void update(@RequestBody @Valid UpdateCategoriesRequest updatecategoriesRequest){
        this.categoriesService.update(updatecategoriesRequest);
    }
    @DeleteMapping
    public void delete(@PathVariable Long id){
        this.categoriesService.delete(id);
    }
}
