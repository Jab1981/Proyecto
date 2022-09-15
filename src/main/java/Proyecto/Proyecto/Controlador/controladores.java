package Proyecto.Proyecto.Controlador;

import Proyecto.Proyecto.Modelos.Empresa;
import Proyecto.Proyecto.Repositorios.RepositorioMovimientos;
import Proyecto.Proyecto.Servicios.ServicioEmpresa;
import Proyecto.Proyecto.Servicios.ServicioMovimientos;
import Proyecto.Proyecto.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class controladores {
    @Autowired
    ServicioEmpresa servicioEmpresa;
    @Autowired
    ServicioUsuario servicioUsuario;
    @Autowired
    ServicioMovimientos servicioMovimientos;

    @Autowired
    RepositorioMovimientos repositorioMovimientos;

    //EMPRESAS
    @GetMapping({"/","/EmpresaHtml"})
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=servicioEmpresa.getAllEmpresas();
        model.addAttribute("emplist",listaEmpresas);
        model.addAttribute("mensaje",mensaje);
        return "EmpresaHtml";
    }

}
