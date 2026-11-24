package ex1;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Parties 1.1 et 1.2 : lit le formulaire (POST), affiche un tableau HTML
 * et cree un cookie pour chaque champ.
 * Deux URL pointent vers la meme servlet (question 1.1.3).
 */
@WebServlet(urlPatterns = {"/FormServlet", "/Form_Servlet"})
public class FormServlet extends HttpServlet {

    private static final int ONE_DAY = 24 * 60 * 60; // duree de vie des cookies, en secondes

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Le texte entre guillemets doit etre identique a l'attribut name du formulaire
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String birthDay  = request.getParameter("birthDay");
        String sex       = request.getParameter("sex");

        // Les cookies doivent etre ajoutes AVANT d'ecrire le corps de la reponse
        addCookie(response, "firstName", firstName);
        addCookie(response, "lastName", lastName);
        addCookie(response, "birthDay", birthDay);
        addCookie(response, "sex", sex);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>FormServlet</title></head><body>");
        out.println("<h2>Donnees recues par " + request.getServletPath() + "</h2>");
        out.println("<table border='1' cellpadding='5'>");
        out.println("<tr><th>First name</th><th>Last name</th><th>Birth date</th><th>Sex</th></tr>");
        out.println("<tr><td>" + escape(firstName) + "</td><td>" + escape(lastName) + "</td><td>"
                + escape(birthDay) + "</td><td>" + escape(sex) + "</td></tr>");
        out.println("</table>");
        out.println("<p>4 cookies ont ete crees (firstName, lastName, birthDay, sex).</p>");
        out.println("<p><a href='ReadCookie'>Lire les cookies</a> | <a href='form.html'>Retour au formulaire</a></p>");
        out.println("</body></html>");
    }

    /**
     * Une URL tapee dans la barre d'adresse envoie un GET. Sans cette methode,
     * Tomcat repond 405 ; on renvoie plutot vers le formulaire.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("form.html");
    }

    private void addCookie(HttpServletResponse response, String name, String value) {
        if (value == null) {
            return;
        }
        // Les valeurs de cookie n'acceptent pas les espaces ni certains caracteres : on les encode
        Cookie cookie = new Cookie(name, java.net.URLEncoder.encode(value, java.nio.charset.StandardCharsets.UTF_8));
        cookie.setMaxAge(ONE_DAY);
        response.addCookie(cookie);
    }

    static String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
