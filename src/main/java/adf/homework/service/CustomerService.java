package adf.homework.service;

import java.util.List;

import adf.homework.domain.Customer;
import adf.homework.dto.CustomerDTO;

public interface CustomerService {

    Customer getCustomerDomainObject(long id);
    List<CustomerDTO> getCustomers();

}
