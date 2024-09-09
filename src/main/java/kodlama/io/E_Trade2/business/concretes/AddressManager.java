package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.AddressService;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.AddressRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateAddressRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateAddressRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllAddressResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdAddressResponse;
import kodlama.io.E_Trade2.entities.concretes.Address;
import kodlama.io.E_Trade2.entities.concretes.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressManager implements AddressService {

    private AddressRepository addressRepository;
    private ModelMapperService modelMapperService;


    @Override
    public List<GetAllAddressResponse> getAll() {
        List<Address> addresses = this.addressRepository.findAll();
        List<GetAllAddressResponse> getAllAddressResponses = addresses.stream()
                .map(address -> {
                    GetAllAddressResponse getAllAddressResponse = new GetAllAddressResponse();
                    getAllAddressResponse.setCity(address.getCity());
                    getAllAddressResponse.setStreetAddress(address.getStreetAddress());
                    getAllAddressResponse.setCountry(address.getCountry());
                    getAllAddressResponse.setState(address.getState());
                    getAllAddressResponse.setPostalCode(address.getPostalCode());
                    getAllAddressResponse.setCustomerId(address.getCustomer() != null ? address.getCustomer().getId() : null);
                    return getAllAddressResponse;
                }).collect(Collectors.toList());

        return getAllAddressResponses;
    }

    @Override
    public GetByIdAddressResponse getById(Long id) {
        return null;
    }

    @Override
    public void add(CreateAddressRequest createAddressRequest) {

    }

    @Override
    public void update(Long customerId, UpdateAddressRequest updateAddressRequest) {

    }

    @Override
    public void delete(Long id) {

    }
}
