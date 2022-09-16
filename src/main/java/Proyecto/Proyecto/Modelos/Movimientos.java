package Proyecto.Proyecto.Modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fecha;

    public Movimientos() {
    }

    public Movimientos(Long monto, String concepto, Usuario usuario, Date fecha) {
        this.monto = monto;
        this.concepto = concepto;
        this.usuario = usuario;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
