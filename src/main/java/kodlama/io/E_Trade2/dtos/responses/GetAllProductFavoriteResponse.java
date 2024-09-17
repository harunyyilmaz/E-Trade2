package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProductFavoriteResponse {

    private Long id;
    private Long customerId;
    private String customerName;
    private Long productId;
    private String productName;
}
