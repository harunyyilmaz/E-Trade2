package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.CommentService;
import kodlama.io.E_Trade2.business.rules.CommentBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.CommentRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.ProductsRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateCommentRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCategoriesRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateCommentRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllCommentResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdCommentResponse;
import kodlama.io.E_Trade2.entities.concretes.Comment;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import kodlama.io.E_Trade2.entities.concretes.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentManager implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapperService modelMapperService;
    private ProductsRepository productsRepository;
    private CustomersRepository customersRepository;
    private CommentBusinessRules commentBusinessRules;

    @Override
    public List<GetAllCommentResponse> getAll() {

        List<Comment> comments = this.commentRepository.findAll();

        List<GetAllCommentResponse> getAllCommentResponses = comments.stream()
                .map(comment -> {
                    GetAllCommentResponse getAllCommentResponse = new GetAllCommentResponse();
                    Product product = this.productsRepository.findByName(comment.getProduct().getName())
                            .orElseThrow(() -> new BusinessException("Product name not found"));
                    getAllCommentResponse.setProductName(product.getName());

                    Customer customer = this.customersRepository.findByUserName(comment.getCustomer().getUserName())
                            .orElseThrow(() -> new BusinessException("Customer user name not found"));
                    getAllCommentResponse.setCustomerName(comment.getCustomer().getUserName());

                    getAllCommentResponse.setId(comment.getId());
                    getAllCommentResponse.setCreateAt(comment.getCreateAt());
                    getAllCommentResponse.setContent(comment.getContent());
                    return getAllCommentResponse;
                }).collect(Collectors.toList());

        return getAllCommentResponses;
    }

    @Override
    public List<GetAllCommentResponse> getCommentsByProductId(Long productId) {

        List<Comment> comments = this.commentRepository.findByProductId(productId);

        List<GetAllCommentResponse> getAllCommentResponses = comments.stream()
                .map(comment -> {
                    GetAllCommentResponse getAllCommentResponse = new GetAllCommentResponse();

                    Product product = this.productsRepository.findByName(comment.getProduct().getName())
                            .orElseThrow(() -> new BusinessException("Product name not found with id"));
                    getAllCommentResponse.setProductName(product.getName());

                    Customer customer = this.customersRepository.findByUserName(comment.getCustomer().getUserName())
                            .orElseThrow(() -> new BusinessException("Customer user name not found with id"));
                    getAllCommentResponse.setCustomerName(customer.getUserName());

                    getAllCommentResponse.setCreateAt(comment.getCreateAt());
                    getAllCommentResponse.setContent(comment.getContent());
                    getAllCommentResponse.setId(comment.getId());

                    return getAllCommentResponse;
                }).collect(Collectors.toList());

        return getAllCommentResponses;
    }

    @Override
    public List<GetAllCommentResponse> getCommentsByCustomerId(Long customerId) {

        List<Comment> comments = this.commentRepository.findByCustomerId(customerId);

        List<GetAllCommentResponse> getAllCommentResponses = comments.stream()
                .map(comment -> {
                    GetAllCommentResponse getAllCommentResponse = new GetAllCommentResponse();
                    getAllCommentResponse.setId(comment.getId());
                    getAllCommentResponse.setCustomerName(comment.getCustomer().getUserName());
                    getAllCommentResponse.setProductName(comment.getProduct().getName());
                    getAllCommentResponse.setCreateAt(comment.getCreateAt());
                    getAllCommentResponse.setContent(comment.getContent());
                    return getAllCommentResponse;
                }).collect(Collectors.toList());

        return getAllCommentResponses;
    }

    @Override
    public List<GetAllCommentResponse> getRecentComments(LocalDateTime afterDate) {

        List<Comment> comments = Optional.ofNullable(this.commentRepository.findByCreateAtAfter(afterDate))
                .orElse(Collections.emptyList());


        List<GetAllCommentResponse> getAllCommentResponses = comments.stream()
                .map(comment -> {
                    GetAllCommentResponse getAllCommentResponse = new GetAllCommentResponse();
                    getAllCommentResponse.setId(comment.getId());
                    getAllCommentResponse.setContent(comment.getContent());
                    getAllCommentResponse.setCreateAt(comment.getCreateAt());
                    getAllCommentResponse.setCustomerName(comment.getCustomer().getUserName());
                    getAllCommentResponse.setProductName(comment.getProduct().getName());
                    return getAllCommentResponse;
                }).collect(Collectors.toList());
        return getAllCommentResponses;
    }

    @Override
    public GetByIdCommentResponse getById(Long id) {

        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Comment not found with id"));

        GetByIdCommentResponse getByIdCommentResponse = new GetByIdCommentResponse();
        getByIdCommentResponse.setContent(comment.getContent());
        getByIdCommentResponse.setCustomerName(comment.getCustomer().getUserName());
        getByIdCommentResponse.setProductName(comment.getProduct().getName());
        getByIdCommentResponse.setId(comment.getId());
        getByIdCommentResponse.setCreateAt(comment.getCreateAt());

        return getByIdCommentResponse;
    }

    @Override
    public void add(CreateCommentRequest createCommentRequest) {

        this.commentBusinessRules.checkIfContentExists(createCommentRequest.getContent());
        this.commentBusinessRules.validateCommentTimeConstraint(createCommentRequest.getProductId(), createCommentRequest.getCustomerId());

        //iş kurallarına odaklanır ve ürün ve müşteri ilişkilerinin geçerliliğini ve iş kurallarına uygunluğunu doğrular.
        this.commentBusinessRules.validateCommentAssociations(createCommentRequest.getProductId(), createCommentRequest.getCustomerId());
        Comment comment = new Comment();
        comment.setContent(comment.getContent());

        Product product = this.productsRepository.findById(createCommentRequest.getProductId())
                .orElseThrow(() -> new BusinessException("Product id not found"));
        comment.setProduct(product);

        Customer customer = this.customersRepository.findById(createCommentRequest.getCustomerId())
                .orElseThrow(() -> new BusinessException("Customer id not found"));
        comment.setCustomer(customer);

        this.commentRepository.save(comment);
    }

    @Override
    public void update(Long commentId, UpdateCommentRequest updateCommentRequest) {

        this.commentBusinessRules.checkIfContentExists(updateCommentRequest.getContent());

        Comment comment = this.commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException("Comment not found with id"));

        Optional.ofNullable(updateCommentRequest.getId()).ifPresent(comment::setId);

        Optional.ofNullable(updateCommentRequest.getContent()).ifPresent(comment::setContent);


        Product product = this.productsRepository.findById(updateCommentRequest.getProductId())
                .orElseThrow(() -> new BusinessException("Product not found with id"));
        comment.setProduct(product);

        Customer customer = this.customersRepository.findById(updateCommentRequest.getCustomerId())
                .orElseThrow(() -> new BusinessException("Customer not found with id"));
        comment.setCustomer(customer);

        this.commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        this.commentRepository.deleteById(id);
    }

    @Override // veritabanindan silememis oluruz böylelikle.
    public void deleteComment(Long commentId, Long userId) {

        this.commentBusinessRules.validateDeletePermission(commentId,userId);

        Comment comment = this.commentRepository.findById(userId)
                .orElseThrow(()-> new BusinessException("comment not found"));

        comment.setDeleted(true); // Yumusak silme
        comment.setDeletedAt(LocalDateTime.now());
        this.commentRepository.save(comment);
    }


}
