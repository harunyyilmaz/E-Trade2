package kodlama.io.E_Trade2.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdFavoriteProductResponse {

    private Long id;
    private Long productId;
    private Long customerId;
    private String customerName;
    private String productName;
}
