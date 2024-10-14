package kodlama.io.E_Trade2.core.utilities.mappers;

import kodlama.io.E_Trade2.dtos.responses.GetAllCartItemResponse;
import org.modelmapper.ModelMapper;

public interface ModelMapperService {

    ModelMapper forRequest();

    ModelMapper forResponse();


}
