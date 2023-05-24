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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "socios")
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10)
    private Long id;
    @Column(length = 70,nullable = false)
    private String nombres;
    @Column(length = 70, nullable = false)
    private String apellidos;
    @Column(length = 100, nullable = false)
    private String correo;
    @Column(nullable = false)
    private Boolean activo;
    @Column(length = 12)
    private Integer ci;
    @Column(length = 8)
    private Integer celular;
    @Column(nullable = true, length = 1000000)
    private String foto;
    @Column
    private Date fechaNac;
    @Column(updatable = false)
    private Date fechaReg;
    @Column
    private String direccion;

    @OneToMany(mappedBy = "idSocio",cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Consultas> consultas= new ArrayList<Consultas>();

    @OneToMany(mappedBy = "idSocio",cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Medidor> medidores = new ArrayList<Medidor>();
    
    @OneToOne(mappedBy = "socio")
    @JsonIgnore
    private Usuario user;


    
    
}
