package kodlama.io.E_Trade2.business.concretes;

import kodlama.io.E_Trade2.business.abstracts.AddressService;
import kodlama.io.E_Trade2.business.rules.AddressBusinessRules;
import kodlama.io.E_Trade2.core.utilities.exceptions.BusinessException;
import kodlama.io.E_Trade2.core.utilities.mappers.ModelMapperService;
import kodlama.io.E_Trade2.dataBase.abstracts.AddressRepository;
import kodlama.io.E_Trade2.dataBase.abstracts.CustomersRepository;
import kodlama.io.E_Trade2.dtos.requests.CreateAddressRequest;
import kodlama.io.E_Trade2.dtos.requests.UpdateAddressRequest;
import kodlama.io.E_Trade2.dtos.responses.GetAllAddressResponse;
import kodlama.io.E_Trade2.dtos.responses.GetByIdAddressResponse;
import kodlama.io.E_Trade2.entities.concretes.Address;
import kodlama.io.E_Trade2.entities.concretes.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressManager implements AddressService {

    private AddressRepository addressRepository;
    private ModelMapperService modelMapperService;
    private CustomersRepository customersRepository;
    private AddressBusinessRules addressBusinessRules;

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

        Address address = this.addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Order not found with id"));

        GetByIdAddressResponse getByIdAddressResponse = new GetByIdAddressResponse();
        getByIdAddressResponse.setStreetAddress(address.getStreetAddress());
        getByIdAddressResponse.setCity(address.getCity());
        getByIdAddressResponse.setState(address.getState());
        getByIdAddressResponse.setCountry(address.getCountry());
        getByIdAddressResponse.setPostalCode(address.getPostalCode());
        getByIdAddressResponse.setCustomerId(address.getCustomer() != null ? address.getCustomer().getId() : null);
        getByIdAddressResponse.setCustomerName(address.getCustomer().getFirstName());

        return getByIdAddressResponse;
    }

    @Override
    public void add(CreateAddressRequest createAddressRequest) {
        this.addressBusinessRules.checkIfPostalCodeAddressExists(createAddressRequest.getPostalCode());
        this.addressBusinessRules.checkIfAddressExists(createAddressRequest.getId());
        this.addressBusinessRules.validateCountryAndCity(createAddressRequest.getCountry(), createAddressRequest.getCity());
        Address address = new Address();
        address.setStreetAddress(createAddressRequest.getStreetAddress());
        address.setCity(createAddressRequest.getCity());
        address.setState(createAddressRequest.getState());
        address.setCountry(createAddressRequest.getCountry());
        address.setPostalCode(createAddressRequest.getPostalCode());
        address.setId(createAddressRequest.getId());
        address.setStreetAddress(createAddressRequest.getStreetAddress());
        address.setCreateAt(createAddressRequest.getCreateAt());

        Customer customer = this.customersRepository.findByFirstName(createAddressRequest.getCustomerName())
                .orElseThrow(() -> new BusinessException("Customer name not found"));
        address.setCustomer(customer);

        this.addressRepository.save(address);


    }

    @Override
    public void update(Long customerId, UpdateAddressRequest updateAddressRequest) {

        Customer customer = this.customersRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Customer not found with id"));

        List<Address> addresses = customer.getAddresses();


        //3. Eğer adres listesi boşsa, yeni bir Address oluştur
        Address addressToUpdate = addresses.isEmpty() ? new Address() : addresses.getFirst();

        //Ülke ve sehir dogrulamasi yapiyoruz ve Sehrin ilgili ülkeye ait olup olmadigini dogrular.
        this.addressBusinessRules.validateCountryAndCity(updateAddressRequest.getCountry(), updateAddressRequest.getCity());

        Optional.ofNullable(updateAddressRequest.getStreetAddress()).ifPresent(addressToUpdate::setStreetAddress);
        Optional.ofNullable(updateAddressRequest.getState()).ifPresent(addressToUpdate::setState);
        Optional.ofNullable(updateAddressRequest.getCountry()).ifPresent(addressToUpdate::setCountry);
        Optional.ofNullable(updateAddressRequest.getCity()).ifPresent(addressToUpdate::setCity);
        Optional.ofNullable(updateAddressRequest.getPostalCode()).ifPresent(addressToUpdate::setPostalCode);

        this.addressRepository.save(addressToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.addressRepository.deleteById(id);
    }

    @Override
    public List<GetAllAddressResponse> getByCustomerId(Long customerId) {
        Customer customer = this.customersRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Customer not found wih id"));

        List<Address> addresses = customer.getAddresses(); //Müsterinin adreslerini aliyoruz.

        List<GetAllAddressResponse> getAllAddressResponses = addresses.stream()
                .map(address -> {
                    GetAllAddressResponse getAllAddressResponse = new GetAllAddressResponse();
                    getAllAddressResponse.setPostalCode(address.getPostalCode());
                    getAllAddressResponse.setCity(address.getCity());
                    getAllAddressResponse.setState(address.getState());
                    getAllAddressResponse.setStreetAddress(address.getStreetAddress());
                    getAllAddressResponse.setCountry(address.getCountry());
                    getAllAddressResponse.setCustomerId(address.getCustomer().getId());
                    return getAllAddressResponse;
                }).collect(Collectors.toList());
        return getAllAddressResponses;
    }

    @Override
    public List<GetAllAddressResponse> findByCity(String city) {

        List<Address> addresses = this.addressRepository.findByCity(city);

        List<GetAllAddressResponse> getAllAddressResponses = addresses.stream()
                .map(address -> {
                    GetAllAddressResponse getAllAddressResponse = new GetAllAddressResponse();
                    getAllAddressResponse.setCity(address.getCity());
                    getAllAddressResponse.setStreetAddress(address.getStreetAddress());
                    getAllAddressResponse.setState(address.getState());
                    getAllAddressResponse.setPostalCode(address.getPostalCode());
                    getAllAddressResponse.setState(address.getState());
                    getAllAddressResponse.setCustomerId(address.getId());
                    return getAllAddressResponse;
                }).collect(Collectors.toList());

        return getAllAddressResponses;
    }
}
