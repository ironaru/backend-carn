package project.DB.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Socio;
import project.DB.server.model.Solicitudes;



@CrossOrigin("*")
@RepositoryRestResource(path = "solicitudes", collectionResourceRel = "Solicitudes" )
public interface SolicitudesRepository extends CrudRepository<Solicitudes, Integer> {

    @Query(nativeQuery = false, value = "select u from Solicitudes u where u.idSocio = :socio order by u.fecha desc,u.atendido desc ")
    public List<Solicitudes> findByIdSocio(@Param("socio") Socio socio);

}