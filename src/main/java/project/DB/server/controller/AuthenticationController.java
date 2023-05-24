package project.DB.server.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Medidor;
import project.DB.server.model.Usuario;
import project.DB.server.repository.MedidorRepository;
import project.DB.server.repository.UsuarioRepository;
import project.DB.server.service.UserDetailService;
import project.DB.server.utils.JwtRequest;
import project.DB.server.utils.JwtResponse;
import project.DB.server.utils.JwtUtils;
import project.DB.server.utils.Mensaje;
import project.DB.server.utils.Password;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) {
        try {
            Usuario userDetails = (Usuario) userDetailService.loadUserByUsername(jwtRequest.getUsername());
            if (userDetails == null) {
                System.out.println("Usuario o contraseña incorrecta");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario o contraseña incorrecta"));
            }
            if(!userDetails.isEnabled()){
                System.out.println("Usuario deshabilitado");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario deshabilitado"));
            }
    
            if (bCryptPasswordEncoder.matches(jwtRequest.getPassword(), userDetails.getClave())) {
                String token = this.jwtUtils.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario o contraseña incorrecta"));
        } catch (Exception e) {
            
        System.out.println("Usuario o contraseña incorrecta");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Mensaje("Usuario o contraseña incorrecta"));

        }

    }

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MedidorRepository medidorRepository;

    @GetMapping("/usuario")
    public Usuario obtenerUsuarioActual(Principal principal) {
        return (Usuario) this.userDetailService.loadUserByUsername(principal.getName());
    }

    @GetMapping("/medidoresUsuario")
    public ResponseEntity<Iterable<Medidor>> obtenerMedidoresUsuario(Principal principal) {
        String usuario = this.userDetailService.loadUserByUsername(principal.getName()).getUsername();
        Usuario usuarioRepo = usuarioRepository.findByUsuario(usuario);
        return ResponseEntity.ok(usuarioRepo.getSocio().getMedidores());
    }

    @GetMapping("/medidorUser/{id}")
    public ResponseEntity<Medidor> obtenerMedidorUsuario(Principal principal, @PathVariable Long id) {
        return ResponseEntity.ok(medidorRepository.findById(id).get());
    }
    @GetMapping("/on")
    private ResponseEntity<?> tokenExpirado(){
        return ResponseEntity.ok().build();
    }
    @PutMapping("/password")
    private Boolean changePassword(@RequestBody Password password, Principal principal) {
        Usuario user = (Usuario)userDetailService.loadUserByUsername(principal.getName());
        if(bCryptPasswordEncoder.matches(password.getActual(), user.getPassword())){
            user.setClave(bCryptPasswordEncoder.encode(password.getNueva()));
            usuarioRepository.save(user);
            return true;
        }
        return false;
    }
}