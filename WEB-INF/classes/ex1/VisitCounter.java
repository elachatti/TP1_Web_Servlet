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
 * Partie 1.2 (extra) : compte les visites grace a un cookie "visits".
 */
@WebServlet("/VisitCounter")
public class VisitCounter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int count = 0; // "visits" est le nom du cookie, count est la variable Java

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("visits".equals(c.getName())) {
                    try {
                        count = Integer.parseInt(c.getValue());
                    } catch (NumberFormatException e) {
                        count = 0;
                    }
                }
            }
        }

        count++;

        Cookie visits = new Cookie("visits", String.valueOf(count));
        visits.setMaxAge(365 * 24 * 60 * 60); // un an
        response.addCookie(visits);            // avant d'ecrire la page

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>VisitCounter</title></head><body>");
        if (count == 1) {
            out.println("<h2>Bienvenue ! C'est votre premiere visite.</h2>");
        } else {
            out.println("<h2>Vous avez visite cette page " + count + " fois.</h2>");
        }
        out.println("<p><a href='VisitCounter'>Recharger</a> | <a href='index.html'>Menu</a></p>");
        out.println("</body></html>");
    }
}
