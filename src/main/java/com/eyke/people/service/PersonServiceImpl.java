package com.eyke.people.service;

import com.eyke.people.dto.request.PersonRequestDTO;
import com.eyke.people.dto.response.PersonResponseDTO;
import com.eyke.people.entity.Person;
import com.eyke.people.repository.PersonRepository;
import com.eyke.people.util.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    @Override
    public PersonResponseDTO findById(Long id) {

        return personMapper.toPersonDTO(returnPerson(id));

    }

    @Override
    public List<PersonResponseDTO> findAll() {

        return personMapper.toPeopleDTO( personRepository.findAll());

    }

    @Override
    public PersonResponseDTO register(PersonRequestDTO personDTO) {

        Person person = personMapper.toPerson(personDTO);

        return personMapper.toPersonDTO(personRepository.save(person));

    }

    @Override
    public PersonResponseDTO update(Long id, PersonRequestDTO personDTO) {

        Person person = returnPerson(id);

        personMapper.updatePersonData(person, personDTO);

        return personMapper.toPersonDTO(personRepository.save(person));

    }

    @Override
    public String delete(Long id) {

        personRepository.deleteById(id);

        return "Person id: "+id+" deleted";

    }

    private Person returnPerson(Long id) {

        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person wasn't found on database"));
    }
}

