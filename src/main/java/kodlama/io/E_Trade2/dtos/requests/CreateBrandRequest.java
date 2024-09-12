package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {


    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 200)
    private String description;
    @NotBlank
    private String country;
    @NotBlank
    @Email
    private String contactEmail;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private Long productId;



}
