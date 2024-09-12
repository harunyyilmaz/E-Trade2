package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdProductResponse {

    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateDate;
    private String name;
    private String descriptions;
    private double price;
    private int quantity;
    private Long categoryId;
    private String categoryName;
    private Set<String> customerFirstName;
    private List<Long> orderId;
}
