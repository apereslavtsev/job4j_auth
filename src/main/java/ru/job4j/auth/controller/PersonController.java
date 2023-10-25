package ru.job4j.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService persons;
    private final PasswordEncoder encoder;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @CheckRequestArguments
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return this.persons.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Person with id=" + Integer.toString(id) + " not found"));
    }

    @CheckRequestArguments
    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return this.persons.save(person)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(person)
                );
    }

    @CheckRequestArguments
    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        boolean updated = persons.update(person);
        if (updated) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not updated");
    }

    @CheckRequestArguments
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean deleted = persons.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person is not deleted");
    }

    @PatchMapping("/")
    public ResponseEntity<Person> patch(@RequestBody Person person) throws InvocationTargetException, IllegalAccessException {
        if (person.getPassword() != null && (!"".equals(person.getPassword()))) {
            person.setPassword(encoder.encode(person.getPassword()));
        }
        return persons.patch(person).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Person with id=" + Integer.toString(person.getId()) + " not found"));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
    }
}