package project.DB.server.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Usuario;

@CrossOrigin("*")
@RepositoryRestResource(path = "usuarios", collectionResourceRel = "Usuarios")
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    public Usuario findByUsuario(String usuario);
}