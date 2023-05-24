package project.DB.server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Medidor;



@CrossOrigin("*")
@RepositoryRestResource(path = "medidores", collectionResourceRel = "Medidores")
public interface MedidorRepository extends CrudRepository<Medidor, Long> {

    public Medidor findBySerialAndMarca(String serial, String marca);
    public Medidor findBySerial(String serial);


}