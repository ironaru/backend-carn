package project.DB.server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.DB.server.model.Medidor;
import project.DB.server.model.Socio;
import project.DB.server.repository.MedidorRepository;
import project.DB.server.repository.SocioRepository;
import project.DB.server.utils.Mensaje;

@RestController
@RequestMapping("/medidores")
public class MedidorController {

    @Autowired
    private MedidorRepository medidorRepository;

    @Autowired
    private SocioRepository socioRepository;

    @GetMapping
    private ResponseEntity<Iterable<Medidor>> findAll() {
        List<Medidor> medidores = new ArrayList<Medidor>();
        medidorRepository.findAll().forEach((medidor) -> {
            medidor.getIdSocio().setFoto(null);
            medidores.add(medidor);
        });
        return ResponseEntity.ok(medidores);
    }

    @GetMapping("/socio/{id}")
    private @ResponseBody ResponseEntity<List<Medidor>> findById(@PathVariable Long id) {
        try {
            List<Medidor> medidores = new ArrayList<Medidor>();
            socioRepository.findById(id).get().getMedidores().forEach((medidor) -> {
                medidor.getIdSocio().setFoto(null);
                medidores.add(medidor);
            });
            ;
            return ResponseEntity.ok(medidores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping
    private ResponseEntity<?> saveMedidor(@RequestBody Medidor medidor) {
        try {
            Socio socio = socioRepository.findById(medidor.getIdSocio().getId()).get();
            if (medidorRepository.findBySerialAndMarca(medidor.getSerial(), medidor.getMarca()) == null) {
                medidor.setIdSocio(socio);
                medidorRepository.save(medidor);
                return ResponseEntity.ok().body(new Mensaje("OK"));
            } else if (medidorRepository.findBySerial(medidor.getSerial()) != null) {
                return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Marca no disponible!"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateMedidor(@PathVariable Long id, @RequestBody Medidor medidor) {
        try {
            Medidor medidorRepo = medidorRepository.findById(id).get();
            medidorRepo.setRegInic(medidor.getRegInic());
            medidorRepository.save(medidorRepo);
            String marca = medidor.getMarca().toString();
            String marcaRepo = medidorRepo.getMarca().toString();
            String serial = medidor.getSerial().toString();
            String serialRepo = medidorRepo.getSerial().toString();
            System.out.println(marcaRepo + "      " + marca);
            System.out.println(serialRepo + "      " + serial);
            if (marca.equals(marcaRepo) && serial.equals(serialRepo)) {
                return ResponseEntity.ok().body(new Mensaje("OK"));
            }

            if (!marca.equals(marcaRepo) && !serial.equals(serialRepo)) {
                if (medidorRepository.findBySerialAndMarca(serial, marca) == null) {
                    medidorRepo.setMarca(medidor.getMarca());
                    medidorRepo.setSerial(medidor.getSerial());
                    medidorRepository.save(medidorRepo);
                    return ResponseEntity.ok().body(new Mensaje("OK"));
                } else {
                    return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Marca y serial no disponible!"));
                }
            }
            if (serial.equals(serialRepo) && !marca.equals(marcaRepo)) {
                if (medidorRepository.findBySerialAndMarca(serial, marca) == null) {
                    medidorRepo.setMarca(medidor.getMarca());
                    medidorRepository.save(medidorRepo);
                    return ResponseEntity.ok().body(new Mensaje("OK"));
                } else {
                    return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Marca no disponible"));
                }
            }
            if (!serial.equals(serialRepo) && marca.equals(marcaRepo)) {
                if (medidorRepository.findBySerialAndMarca(serial, marca) == null) {
                    medidorRepo.setSerial(medidor.getSerial());
                    medidorRepository.save(medidorRepo);
                    return ResponseEntity.ok().body(new Mensaje("OK"));
                }else{
                    return ResponseEntity.status(HttpStatus.IM_USED).body(new Mensaje("Marca y serial no disponible!"));
                }

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    private void DeleteById(@PathVariable Long id) {
        medidorRepository.deleteById(id);
    }

}
