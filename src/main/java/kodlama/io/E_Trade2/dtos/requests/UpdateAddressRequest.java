package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {
    @NotNull
    @NotBlank
    private String streetAddress;
    @NotNull
    @NotBlank
    private String city;
    @NotNull
    @NotBlank
    private String state;
    @NotNull
    @NotBlank
    private String postalCode;
    @NotNull
    @NotBlank
    private String country;

}
