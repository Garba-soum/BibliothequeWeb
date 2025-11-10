package web;

import java.io.IOException;
import dao.LivreDAO;
import dao.LivreDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/supprimer-livre")
public class SupprimerLivreServlet extends HttpServlet {
    private final LivreDAO dao = new LivreDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.supprimer(id);
        } catch (Exception e) {
            System.err.println("Erreur suppression : " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/livres");
    }
}
