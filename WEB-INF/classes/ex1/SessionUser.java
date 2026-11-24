package ex1;

import java.io.Serializable;

import jakarta.servlet.http.HttpSessionActivationListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionEvent;

/**
 * Partie 1.4.3 : objet qui est prevenu lui-meme quand on le met dans une session
 * (valueBound) ou qu'on l'en retire (valueUnbound).
 * Il implemente aussi HttpSessionActivationListener : ces methodes ne sont appelees
 * que si Tomcat serialise la session (persistance, cluster).
 * Serializable est necessaire pour qu'une session puisse etre passivee.
 * Pas d'annotation : un binding listener n'a pas besoin d'etre declare.
 */
public class SessionUser implements HttpSessionBindingListener, HttpSessionActivationListener, Serializable {

    private static final long serialVersionUID = 1L;
    private final String username;

    public SessionUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("[SessionUser] valueBound : " + username + " ajoute a la session "
                + event.getSession().getId() + " sous le nom '" + event.getName() + "'");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("[SessionUser] valueUnbound : " + username + " retire de la session "
                + event.getSession().getId());
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("[SessionUser] sessionWillPassivate : session " + se.getSession().getId());
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("[SessionUser] sessionDidActivate : session " + se.getSession().getId());
    }
}
