package Proyecto.Proyecto.Servicios;

import Proyecto.Proyecto.Modelos.Movimientos;
import Proyecto.Proyecto.Repositorios.RepositorioMovimientos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioMovimientos {
    @Autowired
    RepositorioMovimientos repositorioMovimientos;

    public List<Movimientos> getAllMovimientos(){
        List<Movimientos> movimientosList = new ArrayList<>();
        repositorioMovimientos.findAll().forEach(movimientos -> movimientosList.add(movimientos));
        return movimientosList;
    }

    public Movimientos getMovimientoById(Long id){
        return repositorioMovimientos.findById(id).get();
    }

    public boolean saveOrUpdateMovimientos(Movimientos movimientos){
        Movimientos mov=repositorioMovimientos.save(movimientos);
        if (repositorioMovimientos.findById(mov.getId())!=null){
            return true;
        }
        return false;
    }

    public boolean deleteMovimientos(Long id){
        repositorioMovimientos.deleteById(id);
        if(this.repositorioMovimientos.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public ArrayList<Movimientos> obtenerPorUsuario(Long id) {
        return repositorioMovimientos.findByUsuario(id);
    }

    public ArrayList<Movimientos> obtenerPorEmpresa(Long id) {
        return repositorioMovimientos.findByEmpresa(id);
    }

    public Long obtenerSumaMontos(){
        return repositorioMovimientos.SumarMonto();
    }


    public Long MontosPorUsuario(Long id){
        return repositorioMovimientos.MontosPorUsuario(id);
    }


    public Long MontosPorEmpresa(Long id){
        return repositorioMovimientos.MontosPorEmpresa(id);
    }
}
