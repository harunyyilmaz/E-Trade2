package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdAddressResponse {

    private String streetAddress;
    private String state;
    private String city;
    private String country;
    private String postalCode;
    private Long customerId;
    private String customerName;
}
