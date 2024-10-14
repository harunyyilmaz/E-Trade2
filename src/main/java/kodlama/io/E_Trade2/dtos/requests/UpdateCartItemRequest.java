package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotNull;
import kodlama.io.E_Trade2.enums.CartItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemRequest {

    @NotNull
    private Long id;
    @NotNull
    private int quantity;
    @NotNull
    private BigDecimal price;




}
