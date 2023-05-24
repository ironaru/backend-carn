package project.DB.server.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Consumos;



@CrossOrigin("*")
@RepositoryRestResource(path = "consumos", collectionResourceRel = "Consumos")
public interface ConsumosRepository extends CrudRepository<Consumos, Integer> {
    @Query("select u from Consumos u where u.idMedidor.id=?1")
    public List<Consumos> findByIdMedidor(Long id);
    @Query("select u from Consumos u where u.id=?1")
    public Optional<Consumos> findById(Integer id);
}