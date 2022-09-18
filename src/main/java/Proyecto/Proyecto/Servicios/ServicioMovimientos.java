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

    public List<Movimientos> getAllMovimientos(){ //Metodo que me muestra todos los movimientos sin ningn filtro
        List<Movimientos> movimientosList = new ArrayList<>();
        repositorioMovimientos.findAll().forEach(movimiento -> movimientosList.add(movimiento));  //Recorremos el iterable que regresa el metodo findAll del Jpa y lo guardamos en la lista creada
        return movimientosList;
    }

    public Movimientos getMovimientoById(Long id){ //Ver movimientos por id
        return repositorioMovimientos.findById(id).get();
    }

    public boolean saveOrUpdateMovimiento(Movimientos movimientos){ //Guardar o actualizar elementos
        Movimientos mov=repositorioMovimientos.save(movimientos);
        if (repositorioMovimientos.findById(mov.getId())!=null){
            return true;
        }
        return false;
    }

    public boolean deleteMovimiento(Long id){ //Eliminar movimiento por id
        repositorioMovimientos.deleteById(id); //Eliminar usando el metodo que nos ofrece el repositorio
        if(this.repositorioMovimientos.findById(id).isPresent()){ //Si al buscar el movimiento lo encontramos, no se eliminó (false)
            return false;
        }
        return true; //Si al buscar el movimiento no lo encontramos, si lo eliminò (true)
    }

    public ArrayList<Movimientos> obtenerPorUsuario(Long id) { //Obterner teniendo en cuenta el id del empleado
        return repositorioMovimientos.findByUsuario(id);
    }

    public ArrayList<Movimientos> obtenerPorEmpresa(Long id) { //Obtener movimientos teniendo en cuenta el id de la empresa a la que pertencen los empleados que la registraron
        return repositorioMovimientos.findByEmpresa(id);
    }

    //Servicio para ver la suma de todos los montos
    public Long obtenerSumaMontos(){
        return repositorioMovimientos.SumarMonto();
    }

    //Servicio para ver la suma de los montos por empleado
    public Long MontosPorUsuario(Long id){
        return repositorioMovimientos.MontosPorUsuario(id);
    }

    //Servicio para ver la suma de los montos por empresa
    public Long MontosPorEmpresa(Long id){
        return repositorioMovimientos.MontosPorEmpresa(id);
    }

    //servicio que nos deja conseguir el id de un empleado si tenemos su correo
    public Long IdPorCorreo(String Correo){
        return repositorioMovimientos.IdPorCorreo(Correo);
    }
}
