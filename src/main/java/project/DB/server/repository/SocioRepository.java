package project.DB.server.repository;





import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import project.DB.server.model.Socio;



@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
@RepositoryRestResource(path = "socios",collectionResourceRel = "Socios")
public interface SocioRepository extends CrudRepository<Socio, Long> {

    public List<Socio> findByCorreo(String correo);

    public List<Socio> findByCelular(Integer celular);
    public List<Socio> findByCi(Integer ci);
}
