package project.DB.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tarifas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Date fechaInic;
    @Column(nullable = false)
    private Double consumoMax;
    @Column(nullable = false)
    private Double costoUnit;

    @PrePersist
    public void costoPersist(){
        redondeo2d();
    }
    @PreUpdate
    public void costoUpdate(){
        redondeo2d();
    }
    public void redondeo2d(){
        costoUnit = Math.round(costoUnit*100.0)/100.0;
        consumoMax = Math.round(consumoMax*100.0)/100.0;
    }


}
