package project.DB;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

// import java.sql.Date;
// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import project.DB.server.model.Roles;
import project.DB.server.model.Socio;
import project.DB.server.model.Usuario;
import project.DB.server.repository.RolesRepository;
import project.DB.server.repository.UsuarioRepository;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);

	}

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void run(String... args) throws Exception {
		Roles role1 = new Roles(1, "ADMIN", "Administrador del servidor");
		Roles role2 = new Roles(2, "LECTURADOR", "Rol de lecturador");
		Roles role3 = new Roles(3, "CAJERO", "Rol de cajero");
		Roles role4 = new Roles(4, "SOCIO", "Rol de socio");
		rolesRepository.save(role1);
		rolesRepository.save(role2);
		rolesRepository.save(role3);
		rolesRepository.save(role4);
		// Usuario user1 = usuarioRepository.findById(1L).get();
		// user1.setClave(bCryptPasswordEncoder.encode("ronaldo"));
		// usuarioRepository.save(user1);
		if (usuarioRepository.findByUsuario("admin") == null) {
			
			Set<Roles> roles = new HashSet<>();
			roles.add(role1);
			roles.add(role2);
			roles.add(role3);
			roles.add(role4);
			Socio socio = new Socio();
			socio.setId(50L);
			socio.setNombres("ADMINISTRADOR");
			socio.setApellidos("CARN");
			socio.setCorreo("carn@gmail.com");
			socio.setActivo(true);
			Long miliseconds = System.currentTimeMillis();
			socio.setFechaNac(new Date(miliseconds));
			socio.setFechaReg(new Date(miliseconds));
			socio.setDireccion("direccion");
			String password = bCryptPasswordEncoder.encode("12345");
			Usuario user = new Usuario(50L, "admin", password, socio, roles);
			usuarioRepository.save(user);
		}

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8100")
						.allowedMethods("*");
			}
		};
	}
}
