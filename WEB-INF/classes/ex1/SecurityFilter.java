package ex1;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Partie 1.4.1 (question 5) : protege /ListItems.
 * Declare et mappe dans web.xml, apres LoggingFilter.
 */
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // false : on ne veut PAS creer de session, seulement savoir s'il y en a une
        HttpSession session = req.getSession(false);
        boolean connected = (session != null && session.getAttribute("user") != null);

        System.out.println("[SecurityFilter] Before -> " + req.getRequestURI()
                + (connected ? " (utilisateur : " + session.getAttribute("user") + ")" : " (non connecte)"));

        if (connected) {
            chain.doFilter(request, response); // acces autorise : la servlet s'execute
            System.out.println("[SecurityFilter] After  <- " + req.getRequestURI());
        } else {
            // chain.doFilter() n'est pas appele : la servlet ListItems ne s'execute jamais
            System.out.println("[SecurityFilter] Acces refuse, redirection vers login.html");
            res.sendRedirect(req.getContextPath() + "/login.html");
        }
    }
}
