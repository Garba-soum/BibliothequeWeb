package web;

import java.io.IOException;
import java.util.List;

import dao.LivreDAO;
import dao.LivreDAOImpl;
import model.Livre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/livres")
public class ListeLivresServlet extends HttpServlet {
    private final LivreDAO dao = new LivreDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        try {
            List<Livre> livres = dao.trouverTous();
            req.setAttribute("livres", livres);
        } catch (Exception e) {
            req.setAttribute("error", "Impossible de charger la liste : " + e.getMessage());
        }
        req.getRequestDispatcher("/WEB-INF/views/livres.jsp").forward(req, resp);
    }
}
