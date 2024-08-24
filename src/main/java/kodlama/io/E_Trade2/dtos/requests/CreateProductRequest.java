package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateProductRequest {

    @NotNull
    @NotBlank
    @Size(min = 3 ,max=21)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 5, max = 200)
    private String descriptions;
    @NotNull
    @NotBlank
    private double price;
    @NotNull
    @NotBlank
    private int quantity;

    @NotNull
    @NotBlank
    private Long categoryId;
    @NotNull
    @NotBlank
    private String categoryName;
}
