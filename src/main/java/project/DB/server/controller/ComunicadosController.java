package project.DB.server.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Comunicados;
import project.DB.server.model.sort.compareFechaComunicado;
import project.DB.server.repository.ComunicadosRepository;

@RestController
@RequestMapping("/comunicados")
public class ComunicadosController {
    @Autowired
    private ComunicadosRepository comunicadosRepository;

    @GetMapping("/user")
    private ResponseEntity<Iterable<Comunicados>> findAll() {
        List<Comunicados> lista = new ArrayList<Comunicados>();
        Long miliseconds = 86400000L;
        Date hoy = new Date(System.currentTimeMillis());
        comunicadosRepository.findAll().forEach(comunicado->{
            Date publicacion =  comunicado.getFechaInicio();
            Date vigencia =  new Date(miliseconds*comunicado.getVigencia() + comunicado.getFechaInicio().getTime());
            Boolean vigenciaBool = vigencia.compareTo(hoy)>0 || vigencia.toString().equals(hoy.toString());
            Boolean publicacionBool = publicacion.compareTo(hoy)<0 || publicacion.toString().equals(hoy.toString());
            if(vigenciaBool && publicacionBool){
                lista.add(comunicado);
            }
        });
        Collections.sort(lista,new compareFechaComunicado());
        return ResponseEntity.ok(lista);
    }
    @GetMapping
    private ResponseEntity<Iterable<Comunicados>> comunicados() {
        List<Comunicados> lista = new ArrayList<Comunicados>();
        Long miliseconds = 86400000L;
        Date hoy = new Date(System.currentTimeMillis());
        comunicadosRepository.findAll().forEach(comunicado->{
            Date vigencia =  new Date(miliseconds*comunicado.getVigencia() + comunicado.getFechaInicio().getTime());
            Boolean vigenciaBool = vigencia.compareTo(hoy)>0 || vigencia.toString().equals(hoy.toString());
            if(vigenciaBool){
                comunicado.setUser(null);
                lista.add(comunicado);
            }
        });
        Collections.sort(lista,new compareFechaComunicado());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    private @ResponseBody ResponseEntity<Optional<Comunicados>> findById(@PathVariable int id){
        return ResponseEntity.ok(comunicadosRepository.findById(id));
    }
    @PostMapping
    private ResponseEntity<Comunicados> save (@RequestBody Comunicados comunicado){
        return ResponseEntity.ok(comunicadosRepository.save(comunicado));
    }
    @PutMapping
    private ResponseEntity<Comunicados> update(@RequestBody Comunicados comunicado){
        Comunicados comunicadoRepo = comunicadosRepository.findById(comunicado.getId()).get();
        comunicadoRepo.setDescripcion(comunicado.getDescripcion());
        comunicadoRepo.setFechaInicio(comunicado.getFechaInicio());
        comunicadoRepo.setVigencia(comunicado.getVigencia());
        return ResponseEntity.ok(comunicadosRepository.save(comunicadoRepo));
    }
    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id){
        comunicadosRepository.deleteById(id);
    }
}
