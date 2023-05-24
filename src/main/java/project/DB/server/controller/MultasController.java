package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Multas;
import project.DB.server.repository.MultasRepository;

@RestController
@RequestMapping
public class MultasController {
    @Autowired
    private MultasRepository multasRepository;

    @GetMapping("/multas")
    private ResponseEntity<Iterable<Multas>> getAllMultas(){
        return ResponseEntity.ok(multasRepository.findAll());
    }
    @GetMapping("/multas/{id}")
    private @ResponseBody ResponseEntity<Optional<Multas>> findById(@PathVariable int id){
        return ResponseEntity.ok(multasRepository.findById(id));
    }
    
}
