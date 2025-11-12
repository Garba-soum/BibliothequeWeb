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
        req.setCharacterEncoding("UTF-8");

        String q = req.getParameter("q");               // champ de recherche
        if (q != null) q = q.trim();

        try {
            List<Livre> livres = (q != null && !q.isEmpty())
                    ? dao.trouverParTitreLike(q)        // SELECT ... LIKE '%q%'
                    : dao.trouverTous();                // liste complète

            req.setAttribute("livres", livres);
            req.setAttribute("q", q);                   // pour re-remplir l’input dans la JSP
        } catch (Exception e) {
            req.setAttribute("error", "Impossible de charger la liste : " + e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/views/livres.jsp").forward(req, resp);
    }
}
