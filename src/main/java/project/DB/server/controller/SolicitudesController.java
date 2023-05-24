package project.DB.server.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import project.DB.server.model.Solicitudes;
import project.DB.server.model.Usuario;
import project.DB.server.repository.SolicitudesRepository;
import project.DB.server.service.UserDetailService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudesController {
    @Autowired
    private SolicitudesRepository solicitudesRepository;

    @Autowired
    private UserDetailService userDetailService;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    private ResponseEntity<Iterable<Solicitudes>> findAll() {
        List<Solicitudes> solicitudes = new ArrayList<Solicitudes>();
        solicitudesRepository.findAll().forEach((solicitud) -> {
            solicitud.getIdSocio().setFoto(null);
            solicitudes.add(solicitud);
        });
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/user")
    private ResponseEntity<Iterable<Solicitudes>> findById(Principal principal) {
        Usuario user = (Usuario) userDetailService.loadUserByUsername(principal.getName());
        List<Solicitudes> solicitudes = new ArrayList<Solicitudes>();

        solicitudesRepository.findByIdSocio(user.getSocio()).forEach((solicitud) -> {
            solicitud.getIdSocio().setFoto(null);
            solicitudes.add(solicitud);
        });
        return ResponseEntity.ok(solicitudes);
    }

    @PostMapping
    private @ResponseBody ResponseEntity<Solicitudes> save(@RequestBody Solicitudes solicitud, Principal principal) {
        try {
            solicitud.setAtendido(false);
            solicitud.setFecha(new Date(System.currentTimeMillis()));
            Usuario user = (Usuario) userDetailService.loadUserByUsername(principal.getName());
            solicitud.setIdSocio(user.getSocio());
            return ResponseEntity.ok(solicitudesRepository.save(solicitud));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    private ResponseEntity<Solicitudes> respuesta(@RequestBody Solicitudes solicitud, Principal principal) {
        try {
            Solicitudes solicitudRepo = (Solicitudes) solicitudesRepository.findById(solicitud.getId()).get();
            Long miliseconds = System.currentTimeMillis();
            solicitudRepo.setFechaAtencion(new Date(miliseconds));
            solicitudRepo.setAtendido(true);
            solicitudRepo.setResultado(solicitud.getResultado());
            return ResponseEntity.ok(solicitudesRepository.save(solicitudRepo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Integer id) {
        solicitudesRepository.deleteById(id);
    }
}
