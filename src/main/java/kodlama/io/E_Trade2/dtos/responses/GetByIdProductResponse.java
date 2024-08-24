package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdProductResponse {

    private Long id;
    private String name;
    private String descriptions;
    private double price;
    private int quantity;
    private Long categoryId;
    private String categoryName;

}
