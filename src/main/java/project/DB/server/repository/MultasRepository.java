package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Multas;



@CrossOrigin("*")
@RepositoryRestResource(path = "multas", collectionResourceRel = "Multas")
public interface MultasRepository extends CrudRepository<Multas, Integer> {

}