package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderItemResponse {

    private int quantity;
    private BigDecimal unitPrice;
    private Long productId;
    private Long orderId;
}
