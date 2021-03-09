package hu.hotelinteractive.issuetracker.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomerGroupDto {

    private List<CustomerGroup> groups;

    public CustomerGroupDto() {
        groups = new ArrayList<>();
    }

    public void addGroup(CustomerGroup group) {
        groups.add(group);
    }

}
