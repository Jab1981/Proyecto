package Proyecto.Proyecto.Servicios;

import Proyecto.Proyecto.Modelos.Usuario;
import Proyecto.Proyecto.Modelos.Empresa;
import Proyecto.Proyecto.Repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {
    @Autowired
    RepositorioUsuario repositorioUsuario;


    public List<Usuario> getAllUsuario(){
        List<Usuario> usuarioList= new ArrayList<>();
        repositorioUsuario.findAll().forEach(usuario -> usuarioList.add(usuario));
        return usuarioList;
    }

    //Metodo para buscar empleados por ID
    public Optional<Usuario> getUsuarioById(Long id){ //Existe optional y asi se podria usar

        return repositorioUsuario.findById(id);
    }

    //Metodo para buscar empleados por empresa
    public ArrayList<Usuario> obtenerPorEmpresa(Long id){
        return repositorioUsuario.findByEmpresa(id);
    }

    //Metodo para guardar o actualizar registros en Empleados
    public boolean saveOrUpdateUsuario(Usuario empl){
        Usuario emp=repositorioUsuario.save(empl);
        if (repositorioUsuario.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }

    //Metodo para eliminar un registro de Empleado por Id
    public boolean deleteUsuario(Long id){
        repositorioUsuario.deleteById(id);
        if(this.repositorioUsuario.findById(id).isPresent()){
            return false;
        }
        return true;
    }

}
