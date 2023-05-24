package project.DB.server.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Consumos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(nullable = false)
    private Date fecha;
    @Column(nullable = false)
    private Double lectura;

    @ManyToOne(optional = false, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "idMedidor")
    private Medidor idMedidor;

    @OneToOne(mappedBy = "idConsumo", cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private Facturas idFactura;


   
}
