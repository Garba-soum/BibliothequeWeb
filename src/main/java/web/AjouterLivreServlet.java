package web;

import java.io.IOException;
import java.util.List;

import dao.GenreDAO;
import dao.GenreDAOImpl;
import dao.LivreDAOImpl;
import model.Genre;
import model.Livre;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ajouter-livre")
public class AjouterLivreServlet extends HttpServlet {
	private GenreDAO genreDAO = new GenreDAOImpl();
    private final LivreDAOImpl livreDAO = new LivreDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Genre> genres = genreDAO.findAll();          // ✅ liste pour le <select>
            req.setAttribute("genres", genres);
        } catch (Exception e) {
            req.setAttribute("error", "Impossible de charger les genres : " + e.getMessage());
        }  
        req.getRequestDispatcher("/WEB-INF/views/ajouterLivre.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String titre  = req.getParameter("titre");
        String auteur = req.getParameter("auteur");
        String isbn   = req.getParameter("isbn");
        String annee  = req.getParameter("annee");
        String genreIdStr = req.getParameter("genreId");  

        try {
            // Construire l’objet sans dépendre d’un constructeur spécifique
            Livre livre = new Livre();
            livre.setTitre(titre);
            livre.setAuteur(auteur);
            livre.setIsbn(isbn);

            // anneePublication : adapte selon ton type (int ou Integer)
            if (annee != null && !annee.trim().isEmpty()) {
                livre.setAnneePublication(Integer.parseInt(annee.trim()));
            } else {
                // si ton attribut est int, mets une valeur par défaut (ex: 0)
                // si c'est Integer, tu peux laisser null
                livre.setAnneePublication(0);
            }

            // genreId (FK)   
            if (genreIdStr != null && !genreIdStr.isEmpty()) {
                livre.setGenreId(Integer.valueOf(genreIdStr));
            } else {
                livre.setGenreId(null); // ou lève une erreur si NOT NULL en BD
            }

            // Insertion
            livreDAO.ajouter(livre);
            resp.sendRedirect(req.getContextPath() + "/livres");

        } catch (Exception e) {
            // En cas d’erreur, on réaffiche le formulaire avec la liste des genres + valeurs saisies
            try { req.setAttribute("genres", genreDAO.findAll()); } catch (Exception ignored) {}
            req.setAttribute("error", "Erreur : " + e.getMessage());
            req.setAttribute("livre", new Object(){ public String t=titre,a=auteur,i=isbn,an=annee,g=genreIdStr; }); // juste pour pré-remplir si besoin
            req.getRequestDispatcher("/WEB-INF/views/ajouterLivre.jsp").forward(req, resp);
        }
    }
}
