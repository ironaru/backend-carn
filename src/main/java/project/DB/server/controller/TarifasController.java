package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Tarifas;
import project.DB.server.repository.TarifasRepository;

@RestController
@RequestMapping("/tarifas")
public class TarifasController {
    
    @Autowired
    private TarifasRepository tarifasRepository;

    @GetMapping
    private ResponseEntity<Iterable<Tarifas>> findAll(){
        return ResponseEntity.ok(tarifasRepository.findAll());
    }

    @GetMapping("/{id}")
    private @ResponseBody ResponseEntity<Optional<Tarifas>> findById(@PathVariable int id){
        return ResponseEntity.ok(tarifasRepository.findById(id));
    }
    @PostMapping
    private ResponseEntity<Tarifas> saveTarifa(@RequestBody Tarifas tarifa){
        return ResponseEntity.ok(tarifasRepository.save(tarifa));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Tarifas> updateTarifa(@PathVariable Integer id, @RequestBody Tarifas tarifa){
        Tarifas tarifaRepo = tarifasRepository.findById(id).get();
        if(tarifa.getFechaInic() == tarifaRepo.getFechaInic()){
            return ResponseEntity.ok(tarifaRepo);
        }
        if(tarifasRepository.findByFechaInic(tarifa.getFechaInic())!= null){
            return ResponseEntity.status(HttpStatus.IM_USED).build();
        }
        return ResponseEntity.ok(tarifaRepo);
    }

}
