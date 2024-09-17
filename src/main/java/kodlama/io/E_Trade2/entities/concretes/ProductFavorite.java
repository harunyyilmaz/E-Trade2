package kodlama.io.E_Trade2.entities.concretes;

import jakarta.persistence.*;
import kodlama.io.E_Trade2.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_favorite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFavorite extends BaseEntity {

    @JoinColumn(name = "customer")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    @JoinColumn(name = "product")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Product product;
}
