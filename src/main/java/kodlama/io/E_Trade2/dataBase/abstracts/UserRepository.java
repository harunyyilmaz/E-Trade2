package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findByEmail(String email); // BU metot SecurtyService sinifini olusturdugumuzda
    //gereken ve sonradan olsuturulan metotdur.

    Optional<User> findByUserName(String name);

    boolean existsByEmail(String email);
}

/*
Belirsizlik Yönetimi: Veritabanında veya veri kaynağında, verilen e-posta veya kullanıcı adı
ile eşleşen bir kullanıcı bulunmayabilir. Bu durumun yönetilmesi gerektiğinden, Optional bu
belirsizliği temsil eder. Eğer Optional kullanmazsanız, metot null döndürebilir ve null ile
 çalışmak daha fazla hata ve belirsizlik yaratabilir.

Null Pointer Exception'ı Önleme: Optional kullanarak null değerlerle karşılaşma riskini
azaltırsınız. Optional ile, bir değerin mevcut olup olmadığını kontrol edebilir ve uygun
şekilde işleyebilirsiniz, bu da NullPointerException hatalarını önlemeye yardımcı olur.

Kullanıcıya Bilgi Verme: Optional, bir değer döndürmediğinde (örneğin, kullanıcı bulunamadığında)
 açıkça bu durumu belirten bir yapıdır. Bu, kodu okuyan kişilere metodun başarılı olup olmadığını
  net bir şekilde gösterir.
Özetle, Optional kullanımı, kodun daha güvenli, anlaşılır ve bakımı daha kolay olmasını sağlar.
 */