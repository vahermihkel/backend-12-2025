package ee.mihkel.webshop.service;

import ee.mihkel.webshop.dto.PersonUpdateDto;
import ee.mihkel.webshop.dto.PersonUpdateRecordDto;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

   public void validate(Person person) {
        if (person.getId() != null) {
            throw new RuntimeException("Cannot sign up with ID");
        }
        if (person.getEmail() == null) {
            throw new RuntimeException("Cannot sign up without email");
        }
        if (!Validations.validateEmail(person.getEmail())) {
            throw new RuntimeException("Email is not valid");
        }
        Person dbPerson = personRepository.findByEmailIgnoreCase(person.getEmail());
       if (dbPerson != null) {
           throw new RuntimeException("Email is already in use");
       }
        if (person.getPassword() == null) {
            throw new RuntimeException("Cannot sign up without password");
        }
       if (person.getPersonalCode() != null && !Validations.validatePersonalCode(person.getPersonalCode())) {
           throw new RuntimeException("Personal code is not valid");
       }
       if (person.getAddress() != null
               && person.getAddress().getZipcode() != null
               && !Validations.validateZipcode(person.getAddress().getZipcode())
       ) {
           throw new RuntimeException("Personal code is not valid");
       }
    }

    public Person updatePerson(Long id, PersonUpdateRecordDto dto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        if (dto.firstName() != null) person.setFirstName(dto.firstName());
        if (dto.lastName() != null) person.setLastName(dto.lastName());
        if (dto.email() != null) person.setEmail(dto.email());
        if (dto.personalCode() != null) person.setPersonalCode(dto.personalCode());
        if (dto.phone() != null) person.setPhone(dto.phone());
        if (dto.address() != null) person.setAddress(dto.address());

        return personRepository.save(person);
    }
}
