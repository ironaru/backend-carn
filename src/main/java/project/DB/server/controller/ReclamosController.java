package project.DB.server.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Reclamos;
import project.DB.server.model.Usuario;
import project.DB.server.repository.ReclamosRepository;
import project.DB.server.service.UserDetailService;

@RestController
@RequestMapping("/reclamos")
public class ReclamosController {

    @Autowired
    private ReclamosRepository reclamosRepository;
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping
    private ResponseEntity<Iterable<Reclamos>> findAll() {
        List<Reclamos> reclamos = new ArrayList<Reclamos>();
        reclamosRepository.findAll().forEach((reclamo)->{
            reclamo.getIdSocio().setFoto(null);
            reclamos.add(reclamo);
        });
        return ResponseEntity.ok(reclamos);
    }

    @GetMapping("/user")
    private ResponseEntity<Iterable<Reclamos>> reclamosUser(Principal principal) {
        Usuario user = (Usuario) userDetailService.loadUserByUsername(principal.getName());
        List<Reclamos> reclamos = new ArrayList<Reclamos>();
        reclamosRepository.findByIdSocio(user.getSocio()).forEach((reclamo)->{
            reclamo.getIdSocio().setFoto(null);
            reclamos.add(reclamo);
        });

        return ResponseEntity.ok(reclamos);
    }

    @PutMapping
    private ResponseEntity<Reclamos> respuesta(@RequestBody Reclamos reclamo, Principal principal) {
        Reclamos reclamoRepo = (Reclamos) reclamosRepository.findById(reclamo.getId()).get();
        Long miliseconds = System.currentTimeMillis();
        reclamoRepo.setAtendido(true);
        reclamoRepo.setFechaAtencion(new Date(miliseconds));
        reclamoRepo.setResultado(reclamo.getResultado());
        return ResponseEntity.ok(reclamosRepository.save(reclamoRepo));
    }
    @PostMapping
    private ResponseEntity<Reclamos> save(@RequestBody Reclamos reclamo, Principal principal) {
        Long miliseconds = System.currentTimeMillis();
        reclamo.setFecha(new Date(miliseconds));
        reclamo.setAtendido(false);
        reclamo.setFechaAtencion(null);
        Usuario user = (Usuario) userDetailService.loadUserByUsername(principal.getName());
        reclamo.setIdSocio(user.getSocio());
        return ResponseEntity.ok(reclamosRepository.save(reclamo));
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id) {
        reclamosRepository.deleteById(id);
    }
}