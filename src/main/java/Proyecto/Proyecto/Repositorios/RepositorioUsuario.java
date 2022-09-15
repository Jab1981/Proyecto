package Proyecto.Proyecto.Repositorios;

import Proyecto.Proyecto.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RepositorioUsuario extends CrudRepository<Usuario, Long> {
    @Query(value="SELECT * FROM Usuario where Empresa_id= ?1", nativeQuery=true)
    public abstract ArrayList<Usuario> findByEmpresa(Long id);

}