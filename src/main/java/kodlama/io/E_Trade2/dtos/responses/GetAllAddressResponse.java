package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAddressResponse {

    private String streetAddress;
    private String city;
    private String postalCode;
    private String state;
    private String country;
    private Long customerId;

}
