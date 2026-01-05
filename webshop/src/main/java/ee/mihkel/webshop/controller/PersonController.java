package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.PersonPublicDto;
import ee.mihkel.webshop.dto.PersonUpdateDto;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
//@AllArgsConstructor
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

//    public PersonController(PersonRepository personRepository, PersonService personService, ModelMapper mapper) {
//        this.personRepository = personRepository;
//        this.personService = personService;
//        this.mapper = mapper;
//    }

    @GetMapping("persons")
    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    @GetMapping("persons-public")
    public List<PersonPublicDto> getPersonsPublic(){
//        List<Person> personsFromDb = personRepository.findAll();
//        List<PersonPublicDto> personsDto = new ArrayList<>();
//        for (Person person : personsFromDb) {
//            PersonPublicDto personPublicDto = new PersonPublicDto();
//            personPublicDto.setFirstName(person.getFirstName());
//            personPublicDto.setLastName(person.getLastName());
//            personsDto.add(personPublicDto);
//        }
//        return personsDto;
        System.out.println(mapper);
        return List.of(mapper.map(personRepository.findAll(), PersonPublicDto[].class));
    }

    @GetMapping("persons/{id}")
    public Person getPersonDetails(@PathVariable Long id){
        return personRepository.findById(id).orElseThrow();
    }

    @PatchMapping("persons/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody PersonUpdateDto dto) {
        return personService.updatePerson(id, dto);
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        personService.validate(person);
        return personRepository.save(person);
    }


}
