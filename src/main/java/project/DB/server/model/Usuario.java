package project.DB.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import project.DB.server.utils.Authority;
import project.DB.server.utils.CustomAuthorityDeserializer;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(length = 16, unique = true)
    private String usuario;

    @NonNull
    @Column
    private String clave;
    
    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE})
    @JsonIgnore
    private List<Comunicados> comunicados = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "id_socio")
    private Socio socio;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "rol_usuarios", joinColumns = @JoinColumn(name = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idRol"))
    private Set<Roles> roles = new HashSet<>();

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        this.roles.forEach(usuarioRol -> {
            autoridades.add(new Authority(usuarioRol.getAutoridad()));
        });
        return autoridades;
    }

    @Override
    public String getPassword() {
        return this.clave;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return socio.getActivo();
    }

    public Usuario(Long id, String usuario,String clave, Socio socio, Set<Roles> roles) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.socio = socio;
        this.roles = roles;
    }
    

}
