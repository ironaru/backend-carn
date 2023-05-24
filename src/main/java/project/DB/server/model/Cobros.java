package project.DB.server.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Cobros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Timestamp fechaHora;
    @Column(nullable = false)
    private Double monto;
    @Column(nullable = false)
    private String ref;
    @Column(nullable = false)
    private Integer tipo;

    @OneToOne
    @JoinColumn(name = "id_factura", nullable = true)
    private Facturas idFactura;

    @ManyToOne(optional = true,cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_multa")
    private Multas idMulta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "socios", nullable = true)
    private Socio socios;

}