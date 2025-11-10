package web;

import dao.GenreDAO;
import dao.GenreDAOImpl;
import dao.LivreDAO;
import dao.LivreDAOImpl;
import model.Genre;
import model.Livre;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/modifier-livre")
public class ModifierLivreServlet extends HttpServlet {
    private final LivreDAO livreDAO = new LivreDAOImpl();
    private final GenreDAO genreDAO = new GenreDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Livre livre = livreDAO.trouverParId(id);              // doit remplir genreId + genre
            List<Genre> genres = genreDAO.findAll();              // pour le <select>

            req.setAttribute("livre", livre);
            req.setAttribute("genres", genres);
            req.getRequestDispatcher("/WEB-INF/views/modifierLivre.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/livres").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));

            Livre l = new Livre();
            l.setTitre(req.getParameter("titre"));
            l.setAuteur(req.getParameter("auteur"));
            l.setIsbn(req.getParameter("isbn"));
            l.setAnneePublication(Integer.parseInt(req.getParameter("annee")));

            String genreIdStr = req.getParameter("genreId");
            l.setGenreId( (genreIdStr == null || genreIdStr.isEmpty()) ? null : Integer.valueOf(genreIdStr) );

            boolean ok = livreDAO.modifier(id, l);                // UPDATE ... genre_id=?
            resp.sendRedirect(req.getContextPath() + "/livres");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp); // réaffiche le formulaire avec erreurs
        }
    }
}
