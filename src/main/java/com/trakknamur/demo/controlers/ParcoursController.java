package com.trakknamur.demo.controlers;

import com.trakknamur.demo.models.dtos.ParcoursDTO;
import com.trakknamur.demo.models.forms.ParcoursForm;
import com.trakknamur.demo.services.ParcoursService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/parcours")
public class ParcoursController {

    private final ParcoursService parcoursService;

    public ParcoursController(ParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    @GetMapping()
    public ResponseEntity<List<ParcoursDTO>> getAllParcours() {

        return ResponseEntity.ok(this.parcoursService.getAll());
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<ParcoursDTO>> getParcoursWithPagination(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(this.parcoursService.getAllWithPagination(page, size));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ParcoursDTO> getOneParcours(@PathVariable(name = "id") long id) {

        return ResponseEntity.ok(this.parcoursService.getOne(id));

    }

    @PostMapping()
    public ResponseEntity<ParcoursDTO> postParcours(@Valid @RequestBody ParcoursForm form) {
        return ResponseEntity.ok(this.parcoursService.insertWithReturnValue(form));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteParcours(@PathVariable(name = "id") long id) {
        return ResponseEntity.ok(this.parcoursService.delete(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ParcoursDTO> updateParcours(@PathVariable(name = "id") long id,@Valid @RequestBody ParcoursForm form) {
        return ResponseEntity.ok(this.parcoursService.update(id, form));
    }

    @PostMapping(path = "/search")
    public ResponseEntity<List<ParcoursDTO>> searchByCriteria(@RequestBody ParcoursForm form) {
        return ResponseEntity.ok(this.parcoursService.search(form));
    }

}
