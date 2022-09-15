package Proyecto.Proyecto.Modelos;

import javax.persistence.*;
import java.util.List;
@Entity
@Table (name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String correo;
    @Enumerated (value = EnumType.STRING)
    private Rol roles;
    @ManyToOne
    @JoinColumn (name = "Empresa_id")
    private Empresa empresa;

    public Usuario() {
    }

    public Usuario(String nombre, String correo, Rol roles, Empresa empresa) {
        this.nombre = nombre;
        this.correo = correo;
        this.roles = roles;
        this.empresa = empresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRoles() {
        return roles;
    }

    public void setRoles(Rol roles) {
        this.roles = roles;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
