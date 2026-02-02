package ee.mihkel.webshop.dto;

import ee.mihkel.webshop.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String personalCode;
    private String phone;
    private Address address;
}
