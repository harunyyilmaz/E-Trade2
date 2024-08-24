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
public class UpdateProductRequest {
    @NotBlank
    @NotBlank
    private Long id;
    @NotBlank
    @NotNull
    @Size(min = 3,max = 21)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 5,max = 200)
    private String descriptions;
    @NotBlank
    @NotNull
    private double price;
    @NotNull
    @NotBlank
    private int quantity;

    @NotBlank
    @NotBlank
    private Long categoryId;

    @NotBlank
    @NotNull
    private String categoryName;
}
