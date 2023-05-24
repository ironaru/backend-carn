package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Comunicados;



@CrossOrigin("*")
@RepositoryRestResource(path = "comunicados", collectionResourceRel = "Comunicados")
public interface ComunicadosRepository extends CrudRepository<Comunicados, Integer> {

}