package Proyecto.Proyecto.Modelos;

import javax.persistence.*;

@Entity
@Table(name = "Movimiento")
public class Movimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long monto;
    private String concepto;
    @ManyToOne
    @JoinColumn (name = "Usuario_id")
    private Usuario usuario;

    public Movimientos() {
    }

    public Movimientos(Long monto, String concepto, Usuario usuario) {
        this.monto = monto;
        this.concepto = concepto;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMonto() {
        return monto;
    }

    public void setMonto(Long monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
