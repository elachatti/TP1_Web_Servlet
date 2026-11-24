package ex1;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Partie 1.4.2 (question 5) : ajoute, remplace et supprime des attributs
 * d'application et de requete pour declencher ApplicationAttributeListener
 * et RequestAttributeListener.
 */
@WebServlet("/AttributeTest")
public class AttributeTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext app = getServletContext();
        app.setAttribute("testApp", "v1");      // added
        app.setAttribute("testApp", "v2");      // replaced
        app.removeAttribute("testApp");         // removed

        request.setAttribute("testReq", "v1");  // added
        request.setAttribute("testReq", "v2");  // replaced
        request.removeAttribute("testReq");     // removed

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>AttributeTest</title></head><body>");
        out.println("<h2>Attributs modifies</h2>");
        out.println("<p>appName (defini par ApplicationListener) = <b>" + app.getAttribute("appName") + "</b></p>");
        out.println("<p>Regardez la console Tomcat : 3 evenements application + 3 evenements requete.</p>");
        out.println("<p><a href='index.html'>Menu</a></p>");
        out.println("</body></html>");
    }
}
