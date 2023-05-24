package project.DB.server.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
// import javax.persistence.Table;
// import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
// @Table(uniqueConstraints = {
//     @UniqueConstraint(name = "UniqueSerialandMarca", columnNames = { "serial", "marca" })
// })
public class Medidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String serial;
    @Column(nullable = false)
    private String marca;
    @Column(name = "registro_inicio", nullable = false)
    private Float regInic;
    @Column(nullable = false)
    private Date fechaInst;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "id_socio", nullable = false)
    private Socio idSocio = new Socio();

    @OneToMany(mappedBy = "idMedidor",cascade ={CascadeType.REMOVE})
    @JsonIgnore
    private List<Consumos> consumos = new ArrayList<Consumos>();

}
