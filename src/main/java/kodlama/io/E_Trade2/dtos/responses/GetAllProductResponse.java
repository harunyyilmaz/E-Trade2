package kodlama.io.E_Trade2.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class GetAllProductResponse {

    private Long id;
    private String name;
    private String descriptions;
    private double price;
    private int quantity;
    private Long categoryId;
    private String categoryName;

}
