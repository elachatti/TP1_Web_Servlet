package ex1;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Partie 1.3.1 : session avec des valeurs simples.
 * - "Welcome on my site" pour un nouveau visiteur, "Welcome back" ensuite ;
 * - compteur de visites stocke dans la session ;
 * - extra : nom complet + date de naissance, nombre de jours avant l'anniversaire ;
 * - partie 1.4.2 : affiche le nombre de sessions actives (ActiveSessionListener).
 */
@WebServlet("/ShowSession")
public class ShowSession extends HttpServlet {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(); // cree la session si elle n'existe pas

        if ("logout".equals(request.getParameter("action"))) {
            session.invalidate();
            response.sendRedirect("ShowSession");
            return;
        }

        // getAttribute() renvoie un Object : on le caste vers le type attendu
        Integer visits = (Integer) session.getAttribute("visits");
        boolean newVisitor = (visits == null);
        visits = newVisitor ? 1 : visits + 1;
        session.setAttribute("visits", visits);

        String fullName = (String) session.getAttribute("name");
        String birthday = (String) session.getAttribute("birthday");
        Object active = getServletContext().getAttribute("activeSessions");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>ShowSession</title></head><body>");
        out.println(newVisitor ? "<h2>Welcome on my site</h2>" : "<h2>Welcome back</h2>");
        out.println("<p>Nombre de visites dans cette session : <b>" + visits + "</b></p>");
        out.println("<p>Sessions actives sur le serveur : <b>" + (active == null ? 0 : active) + "</b></p>");

        if (fullName == null || birthday == null) {
            // Premier passage : on demande le nom complet et la date de naissance
            out.println("<h3>Presentez-vous</h3>");
            out.println("<form action='ShowSession' method='post'>");
            out.println("Full name : <input type='text' name='name' required><br><br>");
            out.println("Date of birth (mm/dd/yyyy) : <input type='text' name='birthday' placeholder='mm/dd/yyyy' required><br><br>");
            out.println("<input type='submit' value='Valider'>");
            out.println("</form>");
        } else {
            long days = daysToBirthday(birthday);
            if (days < 0) {
                out.println("<p>Hi, " + FormServlet.escape(fullName) + ". (date de naissance invalide : "
                        + FormServlet.escape(birthday) + ")</p>");
            } else if (days == 0) {
                out.println("<p>Hi, " + FormServlet.escape(fullName) + ". Happy birthday!</p>");
            } else {
                out.println("<p>Hi, " + FormServlet.escape(fullName) + ". There are " + days
                        + " days to your birthday.</p>");
            }
        }

        out.println("<p>ID de session : " + session.getId() + "</p>");
        out.println("<p><a href='ShowSession'>Recharger</a> | <a href='ShowSession?action=logout'>Invalider la session</a>"
                + " | <a href='index.html'>Menu</a></p>");
        out.println("</body></html>");
    }

    /** Le formulaire est en POST : on enregistre puis on redirige (pattern POST-Redirect-GET). */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        session.setAttribute("name", request.getParameter("name"));
        session.setAttribute("birthday", request.getParameter("birthday"));
        response.sendRedirect("ShowSession");
    }

    /** Nombre de jours jusqu'au prochain anniversaire, ou -1 si la date est invalide. */
    static long daysToBirthday(String birthday) {
        try {
            LocalDate birth = LocalDate.parse(birthday.trim(), FORMAT);
            LocalDate today = LocalDate.now();
            LocalDate next = birth.withYear(today.getYear()); // 29/02 -> 28/02 les annees non bissextiles
            if (next.isBefore(today)) {
                next = birth.withYear(today.getYear() + 1);
            }
            return ChronoUnit.DAYS.between(today, next);
        } catch (DateTimeParseException e) {
            return -1;
        }
    }
}
