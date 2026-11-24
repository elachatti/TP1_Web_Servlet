package ex1;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/** Partie 1.4.2 (question 1) : demarrage et arret de l'application. */
@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("appName", "ING1 Web Application");
        System.out.println("[ApplicationListener] Application demarree : "
                + sce.getServletContext().getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ApplicationListener] Application arretee : "
                + sce.getServletContext().getAttribute("appName"));
    }
}
