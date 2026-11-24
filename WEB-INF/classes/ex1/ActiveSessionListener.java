package ex1;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Partie 1.4.2 (question 2) : compte les sessions actives.
 * AtomicInteger : plusieurs requetes peuvent creer des sessions en meme temps
 * (un thread par requete), un simple int++ ne serait pas sur.
 */
@WebListener
public class ActiveSessionListener implements HttpSessionListener {

    private static final AtomicInteger ACTIVE = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int n = ACTIVE.incrementAndGet();
        se.getSession().getServletContext().setAttribute("activeSessions", n);
        System.out.println("[ActiveSessionListener] Session creee " + se.getSession().getId()
                + " -> sessions actives : " + n);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int n = Math.max(0, ACTIVE.decrementAndGet());
        se.getSession().getServletContext().setAttribute("activeSessions", n);
        System.out.println("[ActiveSessionListener] Session detruite " + se.getSession().getId()
                + " -> sessions actives : " + n);
    }
}
