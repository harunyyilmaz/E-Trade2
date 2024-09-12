package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdAddressResponse {


    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private String streetAddress;
    private String state;
    private String city;
    private String country;
    private String postalCode;
    private Long customerId;
    private String customerName;
}
