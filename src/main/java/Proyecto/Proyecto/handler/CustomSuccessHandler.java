package Proyecto.Proyecto.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            System.out.println("No se puede redireccionar");
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }


    protected String determineTargetUrl(Authentication authentication) {
        String url = "";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> rol = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            rol.add(a.getAuthority());
        }

        if (esAdministrativo(rol)) {
            url = "/EmpresasHtml";
        } else if (esOperativo(rol)) {
            url = "/MovimientosHtml";
        } else {
            url = "/Denegado";
        }
        return url;
    }


    private boolean esOperativo(List<String> rol) {
        if (rol.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean esAdministrativo(List<String> rol) {
        if (rol.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;


    }
}