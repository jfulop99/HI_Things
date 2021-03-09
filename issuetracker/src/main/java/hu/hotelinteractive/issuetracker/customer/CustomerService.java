package hu.hotelinteractive.issuetracker.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> getAllCustomerSortByName() {

        Sort sort = Sort.by("name").ascending();
        return customerRepository.findAll(sort);


    }

}
