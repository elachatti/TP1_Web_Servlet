package ex1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Partie 1.3.2 : stocke les items saisis dans une ArrayList<String> en session.
 * Le formulaire items.html est en POST : l'ajout se fait dans doPost().
 * Extra : affiche combien de fois chaque item a ete saisi.
 * Partie 1.4.1 : cette URL est protegee par SecurityFilter.
 */
@WebServlet("/ListItems")
public class ListItems extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String item = request.getParameter("newItem");

        HttpSession session = request.getSession();
        ArrayList<String> list = getList(session);

        if (item != null && !item.isBlank()) {
            list.add(item.trim());
        }
        // On remet la liste dans la session (obligatoire si elle vient d'etre creee)
        session.setAttribute("items", list);

        display(request, response, list);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        display(request, response, getList(request.getSession()));
    }

    @SuppressWarnings("unchecked") // getAttribute() renvoie un Object : le cast est forcement "unchecked"
    private ArrayList<String> getList(HttpSession session) {
        ArrayList<String> list = (ArrayList<String>) session.getAttribute("items");
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    private void display(HttpServletRequest request, HttpServletResponse response, ArrayList<String> list)
            throws IOException {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>ListItems</title></head><body>");
        if (user != null) {
            out.println("<p>Connecte en tant que <b>" + FormServlet.escape(user) + "</b></p>");
        }
        out.println("<h2>Items de la session (" + list.size() + ")</h2>");

        if (list.isEmpty()) {
            out.println("<p>Aucun item pour le moment.</p>");
        } else {
            out.println("<ol>");
            for (String s : list) {
                out.println("<li>" + FormServlet.escape(s) + "</li>");
            }
            out.println("</ol>");

            out.println("<h3>Nombre de saisies par item</h3>");
            out.println("<table border='1' cellpadding='5'><tr><th>Item</th><th>Saisi</th></tr>");
            for (String s : new LinkedHashSet<>(list)) {
                out.println("<tr><td>" + FormServlet.escape(s) + "</td><td>"
                        + Collections.frequency(list, s) + " fois</td></tr>");
            }
            out.println("</table>");
        }
        out.println("<p><a href='items.html'>Ajouter un item</a> | <a href='Logout'>Deconnexion</a>"
                + " | <a href='index.html'>Menu</a></p>");
        out.println("</body></html>");
    }
}
