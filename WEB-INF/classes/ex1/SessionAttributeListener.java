package ex1;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

/** Partie 1.4.2 (question 4) : ajout / modification / suppression d'un attribut de session. */
@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] Ajout     : " + event.getName() + " = " + event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // getValue() donne l'ANCIENNE valeur ; la nouvelle est dans la session
        Object newValue = event.getSession().getAttribute(event.getName());
        System.out.println("[SessionAttributeListener] Modif     : " + event.getName() + " : "
                + event.getValue() + " -> " + newValue);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("[SessionAttributeListener] Suppr     : " + event.getName() + " (valeur : "
                + event.getValue() + ")");
    }
}
