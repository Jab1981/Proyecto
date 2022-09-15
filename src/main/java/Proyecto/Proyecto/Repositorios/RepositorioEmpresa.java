package Proyecto.Proyecto.Repositorios;

import Proyecto.Proyecto.Modelos.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEmpresa extends JpaRepository<Empresa,Long> {
}
