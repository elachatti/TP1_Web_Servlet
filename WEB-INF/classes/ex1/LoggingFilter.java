package ex1;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Partie 1.4.1 : journalise chaque requete dans la console Tomcat.
 * Version finale : declare dans web.xml (avec le parametre logPrefix),
 * donc SANS @WebFilter (une seule methode d'enregistrement a la fois).
 * Premiere version testee : @WebFilter("/*") au-dessus de la classe.
 */
public class LoggingFilter implements Filter {

    private String prefix = "[LoggingFilter]";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String p = filterConfig.getInitParameter("logPrefix"); // <init-param> de web.xml
        if (p != null && !p.isBlank()) {
            prefix = p;
        }
        System.out.println(prefix + " LoggingFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // ServletRequest ne connait pas l'URL : on caste vers HttpServletRequest
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURL().toString();
        String method = req.getMethod();

        long start = System.nanoTime();
        System.out.println(prefix + " Before servlet -> " + method + " " + url);

        chain.doFilter(request, response); // passe au filtre suivant, puis a la servlet

        double ms = (System.nanoTime() - start) / 1_000_000.0;
        System.out.println(prefix + " After servlet  <- " + method + " " + url
                + String.format(" (%.2f ms)", ms));
    }

    @Override
    public void destroy() {
        System.out.println(prefix + " LoggingFilter destroyed.");
    }
}
