package project.DB.server.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Cobros;
import project.DB.server.model.Socio;


@CrossOrigin("*")
@RepositoryRestResource(path = "cobros", collectionResourceRel = "Cobros")
public interface CobrosRepository extends CrudRepository<Cobros, Integer> {

    List<Cobros> findBySocios(Socio socio);
}