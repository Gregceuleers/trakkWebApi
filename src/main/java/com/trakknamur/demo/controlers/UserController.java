package com.trakknamur.demo.controlers;

import com.trakknamur.demo.models.dtos.UserDTO;
import com.trakknamur.demo.models.forms.UserForm;
import com.trakknamur.demo.services.impl.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userDetailsService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<Boolean> postUser(@RequestBody UserForm form) {
        boolean inserted = this.userDetailsService.insert(form);
        return inserted ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserForm userForm, @PathVariable("id") Long id) {
        return ResponseEntity.ok(this.userDetailsService.update(userForm, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        boolean deleted = this.userDetailsService.delete(id);
        return deleted ? ResponseEntity.ok(true) : ResponseEntity.badRequest().body(false);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserDTO> updateAccessPropertiesUser(@RequestBody Map<String, Object> updates, @PathVariable(name = "id") Long id) throws IllegalAccessException {
        return ResponseEntity.ok(this.userDetailsService.updateAccess(updates, id));
    }
}
