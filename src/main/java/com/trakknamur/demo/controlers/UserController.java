package com.trakknamur.demo.controlers;

import com.trakknamur.demo.models.dtos.UserDTO;
import com.trakknamur.demo.models.forms.UserForm;
import com.trakknamur.demo.services.impl.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(this.userDetailsService.getAll());
    }

    @PostMapping
    public ResponseEntity<Boolean> postUser(@RequestBody UserForm form) {
        boolean validity = this.userDetailsService.insert(form);
        return validity ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }
}
