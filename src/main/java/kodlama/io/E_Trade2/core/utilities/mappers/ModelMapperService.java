package kodlama.io.E_Trade2.core.utilities.mappers;

import org.modelmapper.ModelMapper;

public interface ModelMapperService {

     ModelMapper forRequest();
     ModelMapper forResponse();
}
