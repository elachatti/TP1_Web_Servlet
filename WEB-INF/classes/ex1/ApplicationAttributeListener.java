package ex1;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Partie 1.4.2 (question 5) : attributs de niveau application (ServletContext).
 * Tomcat ajoute lui-meme des attributs internes (org.apache..., jakarta...) :
 * on ne les affiche pas pour garder une console lisible.
 */
@WebListener
public class ApplicationAttributeListener implements ServletContextAttributeListener {

    private boolean isInternal(String name) {
        return name.startsWith("org.apache") || name.startsWith("jakarta.") || name.startsWith("javax.");
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        if (!isInternal(event.getName())) {
            System.out.println("[ApplicationAttributeListener] Ajout : " + event.getName() + " = " + event.getValue());
        }
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        if (!isInternal(event.getName())) {
            System.out.println("[ApplicationAttributeListener] Modif : " + event.getName() + " : " + event.getValue()
                    + " -> " + event.getServletContext().getAttribute(event.getName()));
        }
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        if (!isInternal(event.getName())) {
            System.out.println("[ApplicationAttributeListener] Suppr : " + event.getName());
        }
    }
}
