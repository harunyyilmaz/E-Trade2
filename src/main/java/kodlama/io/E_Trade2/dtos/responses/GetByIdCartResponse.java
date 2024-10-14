package kodlama.io.E_Trade2.dtos.responses;

import kodlama.io.E_Trade2.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdCartResponse {

    private Long cartId;          // Sepetin ID'si
    private BigDecimal totalPrice;
    private LocalDateTime createAt;
    private Long customerId;
    private CartStatus cartStatus; // Sepetin durumu

    //private List<CartItemResponse> items; // Sepetteki ürünlerin listesi

}
