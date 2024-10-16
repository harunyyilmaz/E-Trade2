package kodlama.io.E_Trade2;

import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.exceptions.ProblemDetails;
import kodlama.io.E_Trade2.core.utilities.exceptions.ValidationProblemDetails;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Duration;
import java.util.HashMap;

@SpringBootApplication
@RestControllerAdvice
public class ETrade2Application {

    public static void main(String[] args) {
        SpringApplication.run(ETrade2Application.class, args);
    }


    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    // ProblemDetails döndürecek => hangi hata olursa calisacaksin(BusinessExceptions)
    public ProblemDetails handleBusinessException(BusinessException businessException) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setMessage(businessException.getMessage());

        return problemDetails;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ProblemDetails handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setMessage("VALIDATION:EXCEPTION");
        validationProblemDetails.setValidationErrors(new HashMap<String, String>());

        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validationProblemDetails.getValidationErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return validationProblemDetails;
    }

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Duration commentTimeLimit() {
        return Duration.ofHours(1); // İhtiyacınıza göre süreyi ayarlayın
    }
}
