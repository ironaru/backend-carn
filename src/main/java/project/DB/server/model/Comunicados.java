package project.DB.server.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comunicados {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Date fechaInicio;
    @Column(nullable = false)
    private Integer vigencia;

    @ManyToOne(optional = false)
    private Usuario user;
    
}
