package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {

    @NotNull
    @NotBlank
    private Long id;
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
    @NotNull
    @NotBlank
    private LocalDateTime createAt;
    @NotNull
    @NotBlank
    private String customerName;
}
