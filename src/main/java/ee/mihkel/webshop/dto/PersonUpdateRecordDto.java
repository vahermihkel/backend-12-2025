package ee.mihkel.webshop.dto;

import ee.mihkel.webshop.entity.Address;

public record PersonUpdateRecordDto(
        String firstName,
        String lastName,
        String email,
        String personalCode,
        String phone,
        Address address
) {
}
