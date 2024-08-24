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
public class CreateCustomerRequest {

    @NotNull
    @NotBlank
    @Size(min = 3,max = 27)
    private String firstName;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 27)
    private String lastName;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 27)
    private String userName;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 27)
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 27)
    private String password;
    @NotNull
    @NotBlank
    private int age;
    @NotNull
    @NotBlank
    private int birthYear;

}
