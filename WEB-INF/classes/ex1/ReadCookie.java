package ex1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Partie 1.2 : lit et affiche les quatre cookies.
 * Appelee par un lien, donc par une requete GET : on implemente doGet().
 * ReadCookie?action=delete supprime les cookies (setMaxAge(0)).
 */
@WebServlet("/ReadCookie")
public class ReadCookie extends HttpServlet {

    private static final String[] NAMES = {"firstName", "lastName", "birthDay", "sex"};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean delete = "delete".equals(request.getParameter("action"));

        if (delete) {
            for (String name : NAMES) {
                Cookie c = new Cookie(name, "");
                c.setMaxAge(0); // age 0 = le navigateur supprime le cookie
                response.addCookie(c);
            }
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>ReadCookie</title></head><body>");

        if (delete) {
            out.println("<h2>Cookies supprimes</h2>");
            out.println("<p>Les cookies ont recu un age de 0 : le navigateur les efface.</p>");
        } else {
            out.println("<h2>Cookies recus par le serveur</h2>");
            Cookie[] cookies = request.getCookies(); // peut renvoyer null s'il n'y a aucun cookie
            if (cookies == null) {
                out.println("<p>Aucun cookie trouve.</p>");
            } else {
                out.println("<table border='1' cellpadding='5'><tr><th>Nom</th><th>Valeur</th></tr>");
                for (Cookie c : cookies) {
                    if (isFormCookie(c.getName())) {
                        String value = URLDecoder.decode(c.getValue(), StandardCharsets.UTF_8);
                        out.println("<tr><td>" + c.getName() + "</td><td>" + FormServlet.escape(value) + "</td></tr>");
                    }
                }
                out.println("</table>");
            }
            out.println("<p><a href='ReadCookie?action=delete'>Supprimer les cookies</a></p>");
        }
        out.println("<p><a href='form.html'>Formulaire</a> | <a href='index.html'>Menu</a></p>");
        out.println("</body></html>");
    }

    private boolean isFormCookie(String name) {
        for (String n : NAMES) {
            if (n.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
