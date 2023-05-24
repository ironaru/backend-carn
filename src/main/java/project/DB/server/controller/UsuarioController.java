package project.DB.server.controller;

import java.security.Principal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Roles;
import project.DB.server.model.Usuario;
import project.DB.server.repository.SocioRepository;
import project.DB.server.repository.UsuarioRepository;
import project.DB.server.service.UserDetailService;
import project.DB.server.utils.Mensaje;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailService userDetailService;
    @GetMapping
    private ResponseEntity<Iterable<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<Usuario> findByUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRepository.findById(id).get());
    }

    @GetMapping("/{id}/roles")
    private ResponseEntity<Set<Roles>> roles(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRepository.findById(id).get().getRoles());
    }

    @PostMapping
    private ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
        try {
            if (!socioRepository.findByCi(usuario.getSocio().getCi()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("El CI ya existe!"));
            }
            if (!socioRepository.findByCelular((usuario.getSocio().getCelular())).isEmpty()) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Celular de referencia ocupado!"));
            }
            if (!socioRepository.findByCorreo(usuario.getSocio().getCorreo()).isEmpty()) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Correo ya usado!"));
            }
            // else {
            Roles role = new Roles();
            role.setId(4);
            role.setAutoridad("SOCIO");
            role.setDescripcion("Rol de socio");
            Set<Roles> roles = new HashSet<>();
            roles.add(role);
            usuario.setClave(bCryptPasswordEncoder.encode(usuario.getClave()));
            usuario.setRoles(roles);
            Long miliseconds = System.currentTimeMillis();
            usuario.getSocio().setFechaReg(new Date(miliseconds));
            return ResponseEntity.ok(usuarioRepository.save(usuario));
            // }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/{id}")
    private void DeleteById(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Usuario user) {
        Usuario userRepo = usuarioRepository.findById(id).get();
        try {
            String ciRepo = userRepo.getSocio().getCi().toString();
            String ci = user.getSocio().getCi().toString();
            String celularRepo = userRepo.getSocio().getCelular().toString();
            String celular = user.getSocio().getCelular().toString();
            String correo = user.getSocio().getCorreo().toString();
            String correoRepo = userRepo.getSocio().getCorreo().toString();
            if (!socioRepository.findByCi(user.getSocio().getCi()).isEmpty() && !ci.equals(ciRepo)) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("El CI ya existe!"));
            }
            if (!socioRepository.findByCelular(user.getSocio().getCelular()).isEmpty()
                    && !celular.equals(celularRepo)) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Celular de referencia ocupado!"));
            }
            if (!socioRepository.findByCorreo(correoRepo).isEmpty() && !correo.equals(correoRepo)) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Correo ya usado!"));
            }
            userRepo.getSocio().setApellidos(user.getSocio().getApellidos());
            userRepo.getSocio().setNombres(user.getSocio().getNombres());
            userRepo.getSocio().setCorreo(user.getSocio().getCorreo());
            userRepo.getSocio().setFechaNac(user.getSocio().getFechaNac());
            userRepo.getSocio().setDireccion(user.getSocio().getDireccion());
            userRepo.getSocio().setActivo(user.getSocio().getActivo());
            userRepo.getSocio().setCi(user.getSocio().getCi());
            userRepo.getSocio().setFoto(user.getSocio().getFoto());
            userRepo.setRoles(user.getRoles());
            usuarioRepository.save(userRepo);
            return ResponseEntity.ok().body(new Mensaje("OK"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PutMapping("/profile")
    private Usuario updateUserProfile(@RequestBody Usuario usuario, Principal principal) {
        Usuario usuarioRepo = (Usuario) userDetailService.loadUserByUsername(principal.getName());
        usuarioRepo.getSocio().setFoto(usuario.getSocio().getFoto());
        return usuarioRepository.save(usuarioRepo);
    }

}