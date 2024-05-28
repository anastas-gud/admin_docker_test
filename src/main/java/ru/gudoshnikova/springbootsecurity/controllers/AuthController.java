package ru.gudoshnikova.springbootsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gudoshnikova.springbootsecurity.models.Person;
import ru.gudoshnikova.springbootsecurity.repositories.PeopleRepository;
import ru.gudoshnikova.springbootsecurity.services.RegistrationService;
import ru.gudoshnikova.springbootsecurity.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PeopleRepository personRepository;
    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService, PeopleRepository personRepository) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.personRepository = personRepository;
    }

    @GetMapping("/login")
    public String login(Model model) {
       // model.addAttribute("people", personRepository.findAll());
        return "auth/login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }
    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        registrationService.register(person);
        return "redirect:/auth/login";
    }
}
