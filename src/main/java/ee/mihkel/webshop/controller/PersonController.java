package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.PersonLoginDto;
import ee.mihkel.webshop.dto.PersonPublicDto;
import ee.mihkel.webshop.dto.PersonUpdateDto;
import ee.mihkel.webshop.dto.PersonUpdateRecordDto;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.PersonRole;
import ee.mihkel.webshop.model.AuthToken;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.security.JwtService;
import ee.mihkel.webshop.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
//@AllArgsConstructor
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtService jwtService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


//    public PersonController(PersonRepository personRepository, PersonService personService, ModelMapper mapper) {
//        this.personRepository = personRepository;
//        this.personService = personService;
//        this.mapper = mapper;
//    }

    // PATCH: admin -> customer -> customer
    // PATCH: active -> inactive -> active
    // PATCH: tellimus: makstud, maksmata
    // PATCH: broneering: available false


    @GetMapping("persons")
    public List<Person> getPersons(){
        return personRepository.findAllByOrderByIdAsc();
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

    // kui 1. võtab, siis paneb cache-i, aga võtab andmebaasist
    // kui 2. võtab, siis võtab cache-st
    @GetMapping("profile")
    public Person getMyDetails(){
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return personRepository.findById(id).orElseThrow();
    }

    @Cacheable(value = "userCache", key = "#id")
    @GetMapping("persons/{id}")
    public Person getPersonDetails(@PathVariable Long id){
        return personRepository.findById(id).orElseThrow();
    }

    // iga päring update-b cache-i
    @CachePut(value = "userCache", key = "#result.id")
    @PutMapping("persons")
    public Person updatePerson(@RequestBody PersonUpdateRecordDto dto) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//   Päris rakenduses:   iseenda rolli ei tohiks muuta
        return personService.updatePerson(id, dto);
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        personService.validate(person);
//   Päris rakenduses:   person.setRole(PersonRole.CUSTOMER);
        if (person.getRole() == null) {
            person.setRole(PersonRole.CUSTOMER);
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody PersonLoginDto personDto) {
         Person dbPerson = personRepository.findByEmailIgnoreCase(personDto.getEmail());
         if (dbPerson == null) {
             throw new RuntimeException("Email not found");
         }
//        if (!dbPerson.getPassword().equals(personDto.getPassword())) {
        if (!passwordEncoder.matches(personDto.getPassword(), dbPerson.getPassword())) {
            throw new RuntimeException("Password not correct");
        }
        return jwtService.getToken(dbPerson);
    }

    @CacheEvict(value = "userCache", key = "#personId")
    @PatchMapping("change-admin")
    public List<Person> changeAdmin(@RequestParam Long personId) {
        Person dbPerson =  personRepository.findById(personId).orElseThrow();
        if (dbPerson.getRole().equals(PersonRole.ADMIN)) {
            dbPerson.setRole(PersonRole.CUSTOMER);
        } else {
            dbPerson.setRole(PersonRole.ADMIN);
        }
        personRepository.save(dbPerson);
        return personRepository.findAllByOrderByIdAsc();
    }


}
