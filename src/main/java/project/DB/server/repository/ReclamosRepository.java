package project.DB.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Reclamos;
import project.DB.server.model.Socio;



@CrossOrigin("*")
@RepositoryRestResource(path = "reclamos", collectionResourceRel = "Reclamos")
public interface ReclamosRepository extends CrudRepository<Reclamos, Integer> {

    @Query(nativeQuery = false, value="select u from Reclamos u where u.idSocio = :socio order by u.fecha desc,u.atendido desc")
    List<Reclamos> findByIdSocio(@Param("socio") Socio socio);
}