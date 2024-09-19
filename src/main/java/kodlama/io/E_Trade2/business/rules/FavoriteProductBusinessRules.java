package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.FavoriteProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavoriteProductBusinessRules {

    private FavoriteProductRepository favoriteProductRepository;

    //Favori ürün var mi yok mu.
    public void checkIfFavoriteProductExits(Long productId) {
        if (!this.favoriteProductRepository.existsByProductId(productId)) {
            throw new BusinessException("Favorite product not found with id");
        }
    }

    //Belirtilen müsteri daha önce favorilere eklemis mi.
    public void checkIfProductAlreadyFavorite(Long productId, Long customerId) {
        if (this.favoriteProductRepository.existsByProductIdAndCustomerId(productId, customerId)) {
            throw new BusinessException("Product is already added to favorites for this customer.");
        }
    }
    /*
     existsByProductIdAndCustomerId metodu, veritabanında FavoriteProduct tablosunu veya
     koleksiyonunu sorgular. Bu tablo, favorilere eklenmiş ürünler ile müşteri bilgilerini içerir.
     Bu sorgu, productId ve customerId ile eşleşen bir kayıt olup olmadığını kontrol eder.
     Eşleşen bir kayıt varsa, metod true döner; aksi takdirde false döner.
     */
}