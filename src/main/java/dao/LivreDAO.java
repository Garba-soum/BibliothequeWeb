package dao;

import java.util.List;
import model.Livre;

public interface LivreDAO {
    int ajouter(Livre livre) throws Exception;                // retourne l’ID généré
    List<Livre> trouverTous() throws Exception;
    List<Livre> trouverParTitreLike(String motif) throws Exception; // "%motif%"
    Livre trouverParId(int id) throws Exception;        // <-- AJOUT
    boolean modifier(int id, Livre livre) throws Exception;   // true si 1 ligne MAJ
    boolean supprimer(int id) throws Exception;               // true si 1 ligne SUP
}
