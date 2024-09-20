package kodlama.io.E_Trade2.business.abstracts;

import kodlama.io.E_Trade2.dtos.requests.CreateCommentRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCommentRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCommentResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCommentResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

    List<GetAllCommentResponse> getAll();

    List<GetAllCommentResponse> getCommentsByProductId(Long productId);

    List<GetAllCommentResponse> getCommentsByCustomerId(Long customerId);

    List<GetAllCommentResponse> getRecentComments(LocalDateTime afterDate);

    GetByIdCommentResponse getById(Long id);

    void add(CreateCommentRequest createCommentRequest);

    void update(Long commentId, UpdateCommentRequest updateCommentRequest);

    void delete(Long id);

}
