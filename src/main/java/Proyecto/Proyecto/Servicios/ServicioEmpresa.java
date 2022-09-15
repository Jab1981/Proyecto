package Proyecto.Proyecto.Servicios;

import Proyecto.Proyecto.Modelos.Empresa;
import Proyecto.Proyecto.Repositorios.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioEmpresa {
    @Autowired
    RepositorioEmpresa repositorioEmpresa;

    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresaList = new ArrayList<>();
        repositorioEmpresa.findAll().forEach(empresa -> empresaList.add(empresa));
        return empresaList;
    }


    public Empresa getEmpresaById(Long id){
        return repositorioEmpresa.findById(id).get();
    }


    public boolean saveOrUpdateEmpresa(Empresa empresa){
        Empresa emp=repositorioEmpresa.save(empresa);
        if (repositorioEmpresa.findById(emp.getId())!=null){
            return true;
        }
        return false;
    }


    public boolean deleteEmpresa(Long id){
        repositorioEmpresa.deleteById(id);

        if (repositorioEmpresa.findById(id)!=null){
            return true;
        }
        return false;
    }
}
