package kodlama.io.E_Trade2.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductImageRequest {


    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String url;
    private String thumbnailUrl;
    @NotNull
    @NotBlank
    @Size(min = 3,max = 50)
    private String title;
    @Size(max = 200)
    private String altText;
    @NotNull
    @NotBlank
    private String ProductName;
    private Integer width;
    private Integer height;
    private Long size;
    private String format;
    private Boolean isActive=true;

}
