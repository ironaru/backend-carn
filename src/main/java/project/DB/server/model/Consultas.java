package project.DB.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Consultas  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NonNull
    @Column
    private String detalle;
    @NonNull
    @Column
    private Date fecha;
    @NonNull
    @Column
    private Boolean atendido;
    @Column
    private Date fechaAtencion;
    @Column
    private String resultado;
    
    @ManyToOne
    @JoinColumn(name = "id_socio")
    private Socio idSocio;
}
