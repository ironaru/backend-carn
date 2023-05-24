package project.DB.server.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Consumos;
import project.DB.server.model.sort.compareFechaConsumos;
import project.DB.server.repository.ConsumosRepository;

@RestController
@RequestMapping("/consumos")
public class ConsumosController {

    @Autowired
    private ConsumosRepository consumosRepository;
    @GetMapping
    private @ResponseBody ResponseEntity<Iterable<Consumos>> getAllConsumos(){
        return ResponseEntity.ok(consumosRepository.findAll());
    }

    @GetMapping("/{id}")
    private @ResponseBody ResponseEntity<Iterable<Consumos>> findById(@PathVariable Long id){
        List<Consumos>  consumos =  new ArrayList<>();
        consumosRepository.findByIdMedidor(id).forEach(consumo->{
            if(consumo.getIdFactura() == null || !consumo.getIdFactura().getEstado()){
                consumos.add(consumo);
            }
        });
        Collections.sort(consumos,new compareFechaConsumos());
        return ResponseEntity.ok(consumos);
    }

    @PostMapping
    private ResponseEntity<Consumos> save(@RequestBody Consumos consumo){

        return ResponseEntity.ok(consumosRepository.save(consumo));
    }
}
