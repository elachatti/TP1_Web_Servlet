package ex1;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/** Invalide la session (tests de SecurityFilter et d'ActiveSessionListener). */
@WebServlet("/Logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // ne cree pas de session si aucune n'existe
        if (session != null) {
            session.invalidate(); // -> sessionDestroyed() + valueUnbound()
        }
        response.sendRedirect("login.html");
    }
}
