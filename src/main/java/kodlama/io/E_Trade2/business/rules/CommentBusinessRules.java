package kodlama.io.E_Trade2.business.rules;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.dataBase.abstracts.CommentRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.UserRepository;
import kodlama.io.E_Trade2.entities.concretes.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CommentBusinessRules {

    private CommentRepository commentRepository;
    private Duration timeLimit;
    private UserRepository userRepository;
    private ProductsRepository productsRepository;
    private CustomersRepository customersRepository;

    public void checkIfContentExists(String content) {
        if (this.commentRepository.existsByContent(content)) {
            throw new BusinessException("Content already exists");
        }
    }

    //Yorum yapan bellir bir saate sonra tekrar yorum yapabilir kurali.
    public void validateCommentTimeConstraint(Long productId, Long customerId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limitTime = LocalDateTime.now().minus(timeLimit);

        boolean hasRecentComment = this.commentRepository.existsByProductIdAndCustomerIdAndCreateAtAfter
                (productId, customerId, limitTime);

        if (hasRecentComment) {
            throw new BusinessException
                    ("You can only comment once per" + timeLimit.toHours() + "hours for the same product");
        }
    }

    public void validateDeletePermission(Long commentId, Long userId) {
        // Yorumun var olup olmadığını kontrol et
        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("Comment not found"));

        // Kullanıcının yorumu silme iznine sahip olup olmadığını kontrol et
        boolean isCommentOwner = comment.getCustomer().getId().equals(userId);

        boolean isAdmin = this.userRepository.findById(userId)
                .map(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN")))
                .orElse(false);//Kullanici bulunamazsa faulse döner.

        if (!isCommentOwner && !isAdmin) {
            throw new BusinessException("You do not have permission to delete this comment");
        }

    }

    public void validateCommentAssociations(Long productId, Long customerId) {
        // Ürünün var olup olmadığını kontrol et
        if (!productsRepository.existsById(productId)) {
            throw new BusinessException("Product with ID " + productId + " does not exist.");
        }

        // Müşterinin var olup olmadığını kontrol et
        if (!customersRepository.existsById(customerId)) {
            throw new BusinessException("Customer with ID " + customerId + " does not exist.");
        }
    }
}
