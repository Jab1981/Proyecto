package Proyecto.Proyecto.Repositorios;


import Proyecto.Proyecto.Modelos.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMovimientos extends JpaRepository<Movimientos,Long> {
}