package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllOrderItemResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private int quantity;
    private BigDecimal unitPrice;
    private Long productId;
    private Long orderId;
}
