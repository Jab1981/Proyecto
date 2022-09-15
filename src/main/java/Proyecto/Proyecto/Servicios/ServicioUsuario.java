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


    public List<Usuario> getAllEmpleado(){
        List<Usuario> usuarioList= new ArrayList<>();
        repositorioUsuario.findAll().forEach(usuario -> usuarioList.add(usuario));
        return usuarioList;
    }

        public Optional<Usuario> getUsuarioById(Long id){
        return repositorioUsuario.findById(id);
    }
    public ArrayList<Usuario> obtenerPorEmpresa(Long id){
        return repositorioUsuario.findByEmpresa(id);
    }

    public boolean saveOrUpdateEmpleado(Usuario user){
        Usuario emp=repositorioUsuario.save(user);
        if (repositorioUsuario.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }


    public boolean deleteUsuario(Long id){
       repositorioUsuario.deleteById(id);
        if(this.repositorioUsuario.findById(id).isPresent()){
            return false;
        }
        return true;
    }

}
