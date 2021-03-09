package hu.hotelinteractive.issuetracker.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomerGroupService {

    private CustomerGroupRepository customerGroupRepository;

    public List<CustomerGroup> findAll() {
        return StreamSupport.stream(customerGroupRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteById(long id) {
        customerGroupRepository.deleteById(id);
    }

}
