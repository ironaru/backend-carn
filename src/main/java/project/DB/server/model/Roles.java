package project.DB.server.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@NoArgsConstructor
@Entity
public class Roles {
    @Id
    @Basic(fetch = FetchType.EAGER)
    private Integer id;
    @Column(nullable = false)
    private String autoridad;
    @Column(nullable = false)
    private String descripcion;
    
    public Roles(Integer id, String autoridad, String descripcion) {
        this.id = id;
        this.autoridad = autoridad;
        this.descripcion = descripcion;
    }

    @ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();

}
