package lab.mvc.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lab.mvc.model.Person;

/**
 * Partie 3.3 : le Controleur.
 * Lit le formulaire, cree le bean Person (Modele), le place dans la requete,
 * puis transmet la requete a la bonne Vue (old.jsp ou young.jsp).
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Person person = new Person(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("birthDay"),
                request.getParameter("sex"));

        request.setAttribute("person", person); // portee "request" : visible par la JSP cible

        String view = (person.getBirthYear() > 0 && person.getBirthYear() < 1990) ? "/old.jsp" : "/young.jsp";

        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
        // L'enonce propose include() ; forward() marche aussi. include() insere la sortie
        // de la JSP dans la reponse de la servlet, forward() lui cede entierement la main.
        dispatcher.include(request, response);
    }
}
