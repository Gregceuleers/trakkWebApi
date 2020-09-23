package com.trakknamur.demo.controlers;

import com.trakknamur.demo.models.dtos.TrouDTO;
import com.trakknamur.demo.models.forms.TrouForm;
import com.trakknamur.demo.services.TrouService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/trous")
public class TrouController implements BaseController<TrouForm, TrouDTO, Long>  {

    private final TrouService trouService;

    public TrouController(TrouService trouService) {
        this.trouService = trouService;
    }

    @GetMapping
    public ResponseEntity<List<TrouDTO>> getAll() {
        return ResponseEntity.ok(this.trouService.getAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TrouDTO> getOne(@PathVariable(name = "id") Long id) {

        return ResponseEntity.ok(this.trouService.getOne(id));

    }

    @PostMapping()
    public ResponseEntity<TrouDTO> insert(@Valid @RequestBody TrouForm form) throws Exception {
        return ResponseEntity.ok(this.trouService.insertWithReturnValue(form));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.trouService.delete(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TrouDTO> update(@Valid @RequestBody TrouForm form, @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(this.trouService.update(id, form));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<TrouDTO> partialUpdate(@RequestBody Map<String, Object> updates, @PathVariable(name = "id") Long id) throws IllegalAccessException {
        return ResponseEntity.ok(this.trouService.partialUpdate(updates, id));
    }

    /*
        Approche de gestion des exceptions dans le contrôleur
     */
//    @ExceptionHandler({IllegalArgumentException.class})
//    public ResponseEntity<?> handleException() {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("une erreur s'est produite");
//    }

}
