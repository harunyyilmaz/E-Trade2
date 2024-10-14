package kodlama.io.E_Trade2.dtos.responses;

import kodlama.io.E_Trade2.entities.concretes.Brand;
import kodlama.io.E_Trade2.entities.concretes.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Setter
public class GetAllProductResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private String name;
    private String descriptions;
    private BigDecimal price;
    private int quantity;
    private Long categoryId;
    private String categoryName;
    private Brand brand;
    private List<Long> orderId;
    private Set<Long> customerId;


}
