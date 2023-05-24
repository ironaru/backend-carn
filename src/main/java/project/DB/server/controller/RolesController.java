package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Roles;
import project.DB.server.repository.RolesRepository;

@RestController
@RequestMapping
public class RolesController {
    @Autowired
    private RolesRepository rolesRepository;



    @GetMapping("/roles")
    private ResponseEntity<Iterable<Roles>> findAll(){
        return ResponseEntity.ok(rolesRepository.findAll());
    }

    @GetMapping("/roles/{id}")
    private @ResponseBody ResponseEntity<Optional<Roles>> getbyId(@PathVariable int id){
        return ResponseEntity.ok(rolesRepository.findById(id));
    }
    // @GetMapping("/roles/{id}/usuarios")
    // private @ResponseBody ResponseEntity<Set<Usuario>> getUsuarios(@PathVariable int id){
    //     return ResponseEntity.ok(null);
    // }
}
