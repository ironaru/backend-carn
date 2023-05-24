package project.DB.server.repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import project.DB.server.model.Tarifas;



@CrossOrigin("*")
@RepositoryRestResource(path = "tarifas", collectionResourceRel = "Tarifas")
public interface TarifasRepository extends CrudRepository<Tarifas, Integer> {
    @Query("select u from Tarifas u where u.fechaInic<=?1 and u.consumoMax>=?2 order by u.fechaInic desc, u.consumoMax")
    public List<Tarifas> findByFechaInicAndConsumoMax(Date fecha, Double consumo);

    public Tarifas findByFechaInic(Date fechaInic);
    @Query("select u from Tarifas u order by u.fechaInic desc, u.consumoMax")
    public Set<Tarifas> findAll();
}