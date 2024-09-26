package kodlama.io.E_Trade2.webApiControllers;

import jakarta.validation.Valid;
import kodlama.io.E_Trade2.business.abstracts.CommentService;
import kodlama.io.E_Trade2.dtos.requests.CreateCommentRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCommentRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCommentResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping
    public List<GetAllCommentResponse> getAll() {
        return this.commentService.getAll();
    }

    @GetMapping("/product/{productId}")
    public List<GetAllCommentResponse> getCommentsByProductId(@PathVariable Long productId) {
        return this.commentService.getCommentsByProductId(productId);
    }

    @GetMapping("/customer/{customerId}")
    public List<GetAllCommentResponse> getCommentsByCustomerId(@PathVariable Long customerId) {
        return this.commentService.getCommentsByCustomerId(customerId);
    }

    @GetMapping("/recent")
    public List<GetAllCommentResponse> getRecentComment(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime afterDate) {
        return this.commentService.getRecentComments(afterDate);
    }

    @GetMapping("/{id}")
    private GetByIdCommentResponse getById(@PathVariable Long id) {
        return this.commentService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<Void> add(@RequestBody @Valid CreateCommentRequest createCommentRequest) {
        this.commentService.add(createCommentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> update(@PathVariable Long commentId, @RequestBody @Valid UpdateCommentRequest updateCommentRequest) {
        this.commentService.update(commentId, updateCommentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comment/{commentId}/user/{userId}")
    private ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
        this.commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }


}
