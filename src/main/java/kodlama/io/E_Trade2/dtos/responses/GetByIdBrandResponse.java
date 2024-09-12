package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdBrandResponse {

    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private String name;
    private String description;
    private String country;
    private String contactEmail;
    private String phoneNumber;
    private Long productId;
}
