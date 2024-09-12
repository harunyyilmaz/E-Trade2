package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdOrderItemResponse {

    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private int quantity;
    private BigDecimal unitPrice;
    private String productName;
    private Long productId;
    private Long orderId;
}
