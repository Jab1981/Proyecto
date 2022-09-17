package Proyecto.Proyecto.Repositorios;


import Proyecto.Proyecto.Modelos.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface RepositorioMovimientos extends JpaRepository<Movimientos,Long> {
    @Query(value ="select * from Movimientos where Usuario_id= ?1", nativeQuery = true)
    public abstract ArrayList<Movimientos> findByUsuario(Long id);


    @Query(value="select * from Movimientos where Usuario_id in (select id from Usuario where Empresa_id= ?1)", nativeQuery = true)
    public abstract ArrayList<Movimientos> findByEmpresa(Long id);


    @Query(value="SELECT SUM(monto) from Movimientos", nativeQuery = true)
    public abstract Long SumarMonto();

    @Query(value="SELECT SUM(monto) from Movimientos where Usuario_id=?1", nativeQuery = true)
    public abstract Long MontosPorUsuario(Long id); //id usuario

    //Metodo para ver la suma de los movimientos por empresa
    @Query(value="select sum(monto) from Movimientos where Usuario_id in (select id from Usuario where Empresa_id= ?1)", nativeQuery = true)
    public abstract Long MontosPorEmpresa(Long id);

    @Query(value="select id from Usuario where correo=?1", nativeQuery = true)
    public abstract Long IdPorCorreo(String correo);
}