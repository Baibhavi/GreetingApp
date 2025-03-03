package com.greetingapplication.controller;

import com.greetingapplication.customExceptions.ResourceNotFoundException;
import com.greetingapplication.model.Greeting;
import com.greetingapplication.repository.GreetingRepository;
import com.greetingapplication.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

    @Autowired
    private GreetingRepository greetingRepository;
    @Autowired
    private GreetingService greetingService;

    @GetMapping
    public List<Greeting> getGreetings(){
        return greetingRepository.findAll();
    }

    @PostMapping
    public Greeting createGreeting(@RequestBody Greeting greeting){
        return greetingRepository.save(greeting);
    }

    @PutMapping("/{id}")
    public Greeting updateGreeting(@PathVariable Long id, @RequestBody Greeting greetingDetails){
        Greeting greeting = greetingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Greeting not found with id " + id));
        greeting.setMessage(greetingDetails.getMessage());
        return greetingRepository.save(greeting);
    }

    @DeleteMapping("/{id}")
    public void deleteGreeting(@PathVariable Long id){
        greetingRepository.deleteById(id);
    }

    @Autowired
    private GreetingService simpleGreet;
    @GetMapping("/simple")
    public String getSimpleGreeting(){
        return simpleGreet.getSimpleGreeting();
    }
    @PostMapping("/save")
    public Greeting saveGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        return greetingService.saveGreeting(firstName, lastName);
    }
    @GetMapping("getId/{id}")
    public Greeting getGreetingById(@PathVariable Long id){
        return greetingService.getGreetById(id);
    }
    @GetMapping("/getAll")
    public List<Greeting> getAllGreetings(){
        return greetingService.getAllGreetings();
    }
}
