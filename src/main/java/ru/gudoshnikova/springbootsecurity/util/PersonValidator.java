package ru.gudoshnikova.springbootsecurity.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gudoshnikova.springbootsecurity.models.Person;
import ru.gudoshnikova.springbootsecurity.services.PersonDetailService;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailService personDetailService;

    @Autowired
    public PersonValidator(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        try {
            personDetailService.loadUserByUsername(person.getUsername());
        }catch (UsernameNotFoundException ig){
            return;
        }
        errors.rejectValue("username", "", "Username already exists");
    }
}
