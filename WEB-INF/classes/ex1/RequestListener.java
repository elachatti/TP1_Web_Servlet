package ex1;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Partie 1.4.2 (question 3) : debut et fin de chaque requete.
 * Question 6 : ce listener est declare dans web.xml avec <listener>,
 * il n'a donc PAS d'annotation @WebListener.
 */
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
        System.out.println("[RequestListener] Requete initialisee : " + req.getRequestURI());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest req = (HttpServletRequest) sre.getServletRequest();
        System.out.println("[RequestListener] Requete terminee   : " + req.getRequestURI());
    }
}
