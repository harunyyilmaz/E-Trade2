package kodlama.io.E_Trade2.entities.concretes;

import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage extends BaseEntity {

    @Column(name = "url",nullable = false)
    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;//Görselin kücük resmi.

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "title")
    private String title;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "size")
    private Long size; //Görselin boyutu.

    @Column(name = "format")//Görselin dosya formati.
    private String format;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "is_active")
    private Boolean isActive;//Görselin aktif olup olmadigi.

}
