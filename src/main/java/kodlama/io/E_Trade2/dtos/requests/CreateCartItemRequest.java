package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCartItemRequest {

    @NotNull
    private BigDecimal price;
    @NotNull
    private int quantity;

    private LocalDateTime createAt;
    @NotNull
    private Long productId;
    @NotNull
    private Long cartId;
}
