package kodlama.io.E_Trade2.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdProductImageResponse {

    private Long id;
    private String url;
    private String thumbnailUrl;
    private Long productId;
    private LocalDateTime uploadDate;
    private String title;
    private String altText;
    private Long size;
    private String format;
    private Integer width;
    private Integer height;
    private Boolean isActive = true;
}
