package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCategoriesRequest {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 3, max = 21)
    private String name;

    @NotBlank
    @Size(min = 5, max = 200)
    private String descriptions;

}
