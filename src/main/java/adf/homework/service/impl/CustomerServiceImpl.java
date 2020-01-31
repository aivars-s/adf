package adf.homework.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import adf.homework.domain.Customer;
import adf.homework.dto.CustomerDTO;
import adf.homework.exception.ResourceNotFoundException;
import adf.homework.mapper.CustomerMapper;
import adf.homework.repository.CustomerRepository;
import adf.homework.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer getCustomerDomainObject(long id) {
        return customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override public List<CustomerDTO> getCustomers() {
        return customerMapper.toCustomerDTO(customerRepository.findAll());
    }

}
