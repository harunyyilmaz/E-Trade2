package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllAddressResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private String streetAddress;
    private String city;
    private String postalCode;
    private String state;
    private String country;
    private Long customerId;

}
