package Proyecto.Proyecto.Repositorios;

import Proyecto.Proyecto.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
}