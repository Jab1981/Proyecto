package Proyecto.Proyecto.Controlador;

import Proyecto.Proyecto.Modelos.Empresa;
import Proyecto.Proyecto.Modelos.Usuario;
import Proyecto.Proyecto.Repositorios.RepositorioMovimientos;
import Proyecto.Proyecto.Servicios.ServicioEmpresa;
import Proyecto.Proyecto.Servicios.ServicioMovimientos;
import Proyecto.Proyecto.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    //Interfaz empresa Html
    @GetMapping({"/","/EmpresaHtml"})
    public String viewEmpresas(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Empresa> listaEmpresas=servicioEmpresa.getAllEmpresas();
        model.addAttribute("emplist",listaEmpresas);
        model.addAttribute("mensaje",mensaje);
        return "EmpresaHtml";
    }
    @GetMapping("/AgregarEmpresa")
    public String nuevaEmpresa(Model model, @ModelAttribute("mensaje") String mensaje){
        Empresa emp= new Empresa();
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje",mensaje);
        return "agregarEmpresa";
    }

    @PostMapping("/GuardarEmpresa")
    public String guardarEmpresa(Empresa emp, RedirectAttributes redirectAttributes){
        if(servicioEmpresa.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/EmpresaHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarEmpresa";
    }

    @GetMapping("/EditarEmpresa/{id}")
    public String editarEmpresa(Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje){
        Empresa emp=servicioEmpresa.getEmpresaById(id);
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "editarEmpresa";
    }


    @PostMapping("/ActualizarEmpresa")
    public String updateEmpresa(@ModelAttribute("emp") Empresa emp, RedirectAttributes redirectAttributes){
        if(servicioEmpresa.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/EmpresaHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarEmpresa/"+emp.getId();

    }

    @GetMapping("/EliminarEmpresa/{id}")
    public String eliminarEmpresa(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (servicioEmpresa.deleteEmpresa(id)==true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/EmpresaHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/EmpresaHtml";
    }

    @GetMapping ("/UsuarioHtml")
    public String viewUsuario(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Usuario> usuarioList=servicioUsuario.getAllUsuario();
        model.addAttribute("usuarioList",usuarioList);
        model.addAttribute("mensaje",mensaje);
        return "UsuarioHtml";
    }

    @GetMapping("/AgregarUsuario")
    public String nuevoUsuario(Model model, @ModelAttribute("mensaje") String mensaje){
        Usuario user= new Usuario();
        model.addAttribute("user",user);
        model.addAttribute("mensaje",mensaje);
        List<Empresa> empresaList= servicioEmpresa.getAllEmpresas();
        model.addAttribute("usuarioList",empresaList);
        return "agregarUsuario";
    }

    @PostMapping("/GuardarUsuario")
    public String guardarUsuario(Usuario user, RedirectAttributes redirectAttributes){
       // String passEncriptada=passwordEncoder().encode(user.getPassword());
       // user.setPassword(passEncriptada);
        if(servicioUsuario.saveOrUpdateUsuario(user)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/UsuarioHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarUsuario";
    }

    @GetMapping("/EditarUsuario/{id}")
    public String editarUsuario(Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje){
        Usuario user=servicioUsuario.getUsuarioById(id).get();
        model.addAttribute("user",user);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> empresaList= servicioEmpresa.getAllEmpresas();
        model.addAttribute("usuarioList",empresaList);
        return "editarUsuario";
    }

    @PostMapping("/ActualizarUsuario")
    public String updateUsuario(@ModelAttribute("user") Usuario user, RedirectAttributes redirectAttributes){
        //String passEncriptada=passwordEncoder().encode(user.getPassword());
        //user.setPassword(passEncriptada);
        if(servicioUsuario.saveOrUpdateUsuario(user)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/UsuarioHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarUsuario/"+user.getId();

    }

    @GetMapping("/EliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (servicioUsuario.deleteUsuario(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/UsuarioHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/UsuarioHtml";
    }

    @GetMapping("/Empresa/{id}/Usuario") //Filtrar los empleados por empresa
    public String verUsuarioPorEmpresa(@PathVariable("id") Long id, Model model){
        List<Usuario> usuarioList = servicioUsuario.obtenerPorEmpresa(id);
        model.addAttribute("usuarioList",usuarioList);
        return "verUsuario";
    }
}
