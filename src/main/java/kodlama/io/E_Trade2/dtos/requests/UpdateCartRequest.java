package kodlama.io.E_Trade2.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartRequest {


    private Long id;

    private BigDecimal totalPrice;


}
