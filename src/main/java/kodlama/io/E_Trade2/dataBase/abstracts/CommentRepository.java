package kodlama.io.E_Trade2.dataBase.abstracts;

import kodlama.io.E_Trade2.entities.concretes.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByProductId(Long productId);

    List<Comment> findByCustomerId(Long customerId);

    List<Comment> findByCreateAtAfter(LocalDateTime afterDate);
}
