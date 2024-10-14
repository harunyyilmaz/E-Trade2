package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdCartItemResponse {

    private Long id; // CartItem'ın kimliği
    private Long productId; // Ürünün kimliği
    private String productName; // Ürün adı
    private int quantity; // Sepetteki ürün miktarı
    private BigDecimal price; // Ürün fiyatı
}
