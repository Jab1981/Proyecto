package Proyecto.Proyecto.Controlador;

import Proyecto.Proyecto.Modelos.Empresa;
import Proyecto.Proyecto.Modelos.Movimientos;
import Proyecto.Proyecto.Modelos.Usuario;
import Proyecto.Proyecto.Repositorios.RepositorioMovimientos;
import Proyecto.Proyecto.Servicios.ServicioEmpresa;
import Proyecto.Proyecto.Servicios.ServicioMovimientos;
import Proyecto.Proyecto.Servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    //Empresa
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
        return "AgregarEmpresa";
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
    public String EditarEmpresa(Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje){
        Empresa emp=servicioEmpresa.getEmpresaById(id);
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje", mensaje);
        return "EditarEmpresa";
    }


    @PostMapping("/ActualizarEmpresa")
    public String UpdateEmpresa(@ModelAttribute("emp") Empresa emp, RedirectAttributes redirectAttributes){
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
    //Usuario
    //Interfaz Usuario Html
    @GetMapping ("/UsuarioHtml")
    public String viewUsuario(Model model, @ModelAttribute("mensaje") String mensaje){
        List<Usuario> listaUsuarios=servicioUsuario.getAllUsuario();
        model.addAttribute("emplelist",listaUsuarios);
        model.addAttribute("mensaje",mensaje);
        return "UsuarioHtml"; //Llamamos al HTML
    }

    @GetMapping("/AgregarUsuario")
    public String nuevoUsuario(Model model, @ModelAttribute("mensaje") String mensaje){
        Usuario empl= new Usuario();
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje",mensaje);
        List<Empresa> listaEmpresas= servicioEmpresa.getAllEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return "AgregarUsuario"; //Llamar HTML
    }

    @PostMapping("/GuardarUsuario")
    public String guardarUsuario (Usuario empl, RedirectAttributes redirectAttributes){
        String passEncriptada=passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);
        if(servicioUsuario.saveOrUpdateUsuario(empl)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/UsuarioHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarUsuario";
    }

    @GetMapping("/EditarUsuario/{id}")
    public String editarUsuario(Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje){
        Usuario empl=servicioUsuario.getUsuarioById(id).get();
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje", mensaje);
        List<Empresa> listaEmpresas= servicioEmpresa.getAllEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return "EditarUsuario";
    }

    @PostMapping("/ActualizarUsuario")
    public String updateUsuario(@ModelAttribute("empl") Usuario empl, RedirectAttributes redirectAttributes){
        Long id=empl.getId(); //Sacamos el id del objeto empl
        String Oldpass=servicioUsuario.getUsuarioById(id).get().getPassword(); //Con ese id consultamos la contraseña que ya esta en la base
        if(!empl.getPassword().equals(Oldpass)){
           String passEncriptada=passwordEncoder().encode(empl.getPassword());
            empl.setPassword(passEncriptada);
        }
        if(servicioUsuario.saveOrUpdateUsuario(empl)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/UsuarioHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarUsuario/"+empl.getId();

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
        List<Usuario> listaUsuarios = servicioUsuario.obtenerPorEmpresa(id);
        model.addAttribute("listaUsuarios",listaUsuarios);
        return "UsuarioHtml"; //Llamamos al html con el emplelist de los empleados filtrados
    }

    //Movimientos
    //Interfaz Movimientos Html
    @RequestMapping ("/MovimientosHtml")// Controlador que nos lleva al template donde veremos todos los movimientos
    public String viewMovimientos(@RequestParam(value="pagina", required=false, defaultValue = "1") int NumeroPagina,
                                  @RequestParam(value="medida", required=false, defaultValue = "5") int medida,
                                  Model model, @ModelAttribute("mensaje") String mensaje){
        Page<Movimientos> paginaMovimientos= repositorioMovimientos.findAll(PageRequest.of(NumeroPagina,medida));
        model.addAttribute("movlist",paginaMovimientos.getContent());
        model.addAttribute("paginas",new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual", NumeroPagina);
        model.addAttribute("mensaje",mensaje);
        Long sumaMonto=servicioMovimientos.obtenerSumaMontos();
        model.addAttribute("SumaMontos",sumaMonto);//Mandamos la suma de todos los montos a la plantilla
        return "MovimientosHtml"; //Llamamos al HTML
    }

    @GetMapping("/AgregarMovimientos") //Controlador que nos lleva al template donde podremos crear un nuevo movimiento
    public String nuevoMovimientos(Model model, @ModelAttribute("mensaje") String mensaje){
        Movimientos movimiento= new Movimientos();
        model.addAttribute("mov",movimiento);
        model.addAttribute("mensaje",mensaje);
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String correo=auth.getName();
        Long idUsuario=servicioMovimientos.IdPorCorreo(correo);
        model.addAttribute("idUsuario",idUsuario);
        return "AgregarMovimientos"; //Llamar HTML
    }

    @PostMapping("/GuardarMovimientos")
    public String guardarMovimientos(Movimientos mov, RedirectAttributes redirectAttributes){
        if(servicioMovimientos.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/MovimientosHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AgregarMovimientos";
    }

    @GetMapping("/EditarMovimientos/{id}")
    public String editarMovimientos(Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje){
        Movimientos mov=servicioMovimientos.getMovimientoById(id);
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("mov",mov);
        model.addAttribute("mensaje", mensaje);
        List<Usuario> listaUsuario= servicioUsuario.getAllUsuario();
        model.addAttribute("emplelist",listaUsuario);
        return "EditarMovimientos";
    }

    @PostMapping("/ActualizarMovimientos")
    public String updateMovimientos(@ModelAttribute("mov") Movimientos mov, RedirectAttributes redirectAttributes){
        if(servicioMovimientos.saveOrUpdateMovimiento(mov)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/MovimientosHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditarMovimientos/"+mov.getId();

    }

    @GetMapping("/EliminarMovimientos/{id}")
    public String eliminarMovimientos(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (servicioMovimientos.deleteMovimiento(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/MovimientosHtml";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/MovimientosHtml";
    }

    @GetMapping("/Usuario/{id}/Movimientos") //Filtro de movimientos por empleados
    public String movimientosPorUsuario(@PathVariable("id")Long id, Model model){
        List<Movimientos> movlist = servicioMovimientos.obtenerPorUsuario(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto=servicioMovimientos.MontosPorUsuario(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "MovimientosHtml"; //Llamamos al HTML
    }

    @GetMapping("/Empresa/{id}/Movimientos") //Filtro de movimientos por empresa
    public String movimientosPorEmpresa(@PathVariable("id")Long id, Model model){
        List<Movimientos> movlist = servicioMovimientos.obtenerPorEmpresa(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto=servicioMovimientos.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "MovimientosHtml"; //Llamamos al HTML
    }

    //Controlador que me lleva al template de No autorizado
    @RequestMapping(value="/Denegado")
    public String AccesoDenegado(){
        return "AccessDenied";
    }


    //Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
