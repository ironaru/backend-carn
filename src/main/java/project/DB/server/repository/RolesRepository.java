package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Roles;



@CrossOrigin("*")
@RepositoryRestResource(path = "roles", collectionResourceRel = "Roles")
public interface RolesRepository extends CrudRepository<Roles, Integer> {


}