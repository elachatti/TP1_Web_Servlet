package ex1;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Partie 1.4.1 (question 4) : enregistre le nom d'utilisateur dans l'attribut de session "user".
 * Partie 1.4.2 (question 4) : LoginServlet?action=update / remove pour tester SessionAttributeListener.
 * Partie 1.4.3 : stocke aussi un objet SessionUser (HttpSessionBindingListener).
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");

        HttpSession session = request.getSession(); // cree ou recupere la session
        session.setAttribute("user", username);                       // -> attributeAdded (ou Replaced)
        session.setAttribute("sessionUser", new SessionUser(username)); // -> valueBound

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Login</title></head><body>");
        out.println("<h2>Connexion reussie</h2>");
        out.println("<p>Bonjour <b>" + FormServlet.escape(username) + "</b>, vous etes connecte.</p>");
        out.println("<p><a href='items.html'>Aller a la page des items</a></p>");
        out.println("<p>Tests des listeners : <a href='LoginServlet?action=update'>modifier l'attribut user</a> | "
                + "<a href='LoginServlet?action=remove'>supprimer l'attribut user</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if (session == null || action == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Login</title></head><body>");
        if ("update".equals(action)) {
            String old = (String) session.getAttribute("user");
            session.setAttribute("user", old + "_modifie");   // -> attributeReplaced
            out.println("<p>Attribut user modifie : " + FormServlet.escape(old) + "_modifie</p>");
        } else if ("remove".equals(action)) {
            session.removeAttribute("user");                  // -> attributeRemoved
            session.removeAttribute("sessionUser");           // -> valueUnbound
            out.println("<p>Attributs user et sessionUser supprimes. ListItems est de nouveau protege.</p>");
        }
        out.println("<p><a href='ListItems'>ListItems</a> | <a href='login.html'>Login</a> | "
                + "<a href='index.html'>Menu</a></p>");
        out.println("</body></html>");
    }
}
