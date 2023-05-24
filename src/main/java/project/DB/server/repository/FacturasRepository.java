package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Facturas;


@CrossOrigin("*")
@RepositoryRestResource(path = "facturas", collectionResourceRel = "Facturas")
public interface FacturasRepository extends CrudRepository<Facturas, Integer> {

}