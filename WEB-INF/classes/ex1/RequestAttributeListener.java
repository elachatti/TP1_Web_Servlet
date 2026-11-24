package ex1;

import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Partie 1.4.2 (question 5) : attributs de niveau requete.
 * Tomcat pose lui-meme des attributs internes pendant le traitement : on les ignore.
 */
@WebListener
public class RequestAttributeListener implements ServletRequestAttributeListener {

    private boolean isInternal(String name) {
        return name.startsWith("org.apache") || name.startsWith("jakarta.") || name.startsWith("javax.");
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) {
        if (!isInternal(event.getName())) {
            System.out.println("[RequestAttributeListener] Ajout : " + event.getName() + " = " + event.getValue());
        }
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) {
        if (!isInternal(event.getName())) {
            System.out.println("[RequestAttributeListener] Modif : " + event.getName() + " : " + event.getValue()
                    + " -> " + event.getServletRequest().getAttribute(event.getName()));
        }
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) {
        if (!isInternal(event.getName())) {
            System.out.println("[RequestAttributeListener] Suppr : " + event.getName());
        }
    }
}
