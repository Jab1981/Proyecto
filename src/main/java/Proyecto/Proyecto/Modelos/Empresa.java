package Proyecto.Proyecto.Modelos;


import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "Empresa")
public class Empresa {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String nit;

    @OneToMany (mappedBy = "empresa",fetch = FetchType.LAZY)
    private List<Usuario> user;

    public Empresa() {
    }

    public Empresa(String nombre, String direccion, String telefono, String nit, List<Usuario> user) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nit = nit;
        this.user = user;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Usuario> getUser() {
        return user;
    }

    public void setUser(List<Usuario> user) {
        this.user = user;
    }
}
