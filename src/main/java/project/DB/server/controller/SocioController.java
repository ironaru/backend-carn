package project.DB.server.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Socio;
import project.DB.server.repository.SocioRepository;

@RestController
@RequestMapping("/socios")
public class SocioController {
    @Autowired
    private SocioRepository socioRepository;

    @GetMapping
    private ResponseEntity<Iterable<Socio>> getAllSocios() {
        return ResponseEntity.ok(socioRepository.findAll());
    }

    @GetMapping("/{id}")
    private @ResponseBody ResponseEntity<Optional<Socio>> getSocio(@PathVariable long id) {
        Optional<Socio> socio = socioRepository.findById(id);
        socio.get().setFoto(null);
        return ResponseEntity.ok(socio);
    }

    @PutMapping("/{id}")
    private @ResponseBody Socio updateSocio(@PathVariable long id, @RequestBody Socio socio) {
        Optional<Socio> socioRepo = socioRepository.findById(id);
        if (socioRepo.get() != null) {
            socioRepo.get().setCorreo(socio.getCorreo());
            socioRepo.get().setFechaNac(socio.getFechaNac());
            System.out.println(socio.getFechaNac());
            socioRepo.get().setCorreo(socio.getCorreo());
            socioRepo.get().setDireccion(socio.getDireccion());
            return socioRepository.save(socioRepo.get());
        }
        return null;

    }

}
