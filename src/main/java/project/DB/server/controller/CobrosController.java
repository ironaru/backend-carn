package project.DB.server.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Cobros;
import project.DB.server.model.Usuario;
import project.DB.server.repository.CobrosRepository;
import project.DB.server.repository.ConsumosRepository;
import project.DB.server.service.UserDetailService;

@RestController
@RequestMapping("/cobros")
public class CobrosController {
    @Autowired
    private CobrosRepository cobrosRepository;

    @Autowired
    private ConsumosRepository consumosRepository;

    @Autowired
    private UserDetailService userDetailService;
    
    @GetMapping
    private @ResponseBody ResponseEntity<Iterable<Cobros>> getAllCobros(){
        List<Cobros> cobros = new ArrayList<Cobros>();
        cobrosRepository.findAll().forEach((cobro)->{
            cobro.getSocios().setFoto(null);
            cobros.add(cobro);
        });
        return ResponseEntity.ok(cobros);
    }

    @GetMapping("/medidor/{id}")
    private @ResponseBody ResponseEntity<Iterable<Cobros>> findById(@PathVariable Long id){
        List<Cobros>  cobros =  new ArrayList<Cobros>();
        consumosRepository.findByIdMedidor(id).forEach(consumo->{
            if(consumo.getIdFactura() != null){
                if(consumo.getIdFactura().getEstado()){
                    cobros.add(consumo.getIdFactura().getCobros());
                }
            }
        });
        return ResponseEntity.ok(cobros);
    }

    @PostMapping
    private ResponseEntity<Cobros> saveCobro(@RequestBody Cobros cobro){
        return ResponseEntity.ok(cobrosRepository.save(cobro));
    }
    @PutMapping("/{id}")
    private ResponseEntity<Cobros> updateCobro(@PathVariable Integer id, @RequestBody Cobros cobro){
        Cobros cobrosRepo = cobrosRepository.findById(id).get();
        return ResponseEntity.ok(cobrosRepo);
    }
    @GetMapping("user")
    private ResponseEntity<Iterable<Cobros>> cobrosUsuario(Principal principal) {
        Usuario user = (Usuario) userDetailService.loadUserByUsername(principal.getName());
        List<Cobros> cobros = new ArrayList<Cobros>();
        cobrosRepository.findBySocios(user.getSocio()).forEach((cobro)->{
            cobro.getSocios().setFoto(null);
            cobros.add(cobro);
        });
        return ResponseEntity.ok(cobros);
    }

}
