package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Livre;

public class LivreDAOImpl implements LivreDAO { 

	private static final String INSERT_SQL =
			  "INSERT INTO Livres (titre, auteur, isbn, annee_publication, genre_id) VALUES (?, ?, ?, ?, ?)";

			private static final String BASE_SELECT =
			  "SELECT l.id, l.titre, l.auteur, l.isbn, l.annee_publication, l.genre_id, " +
			  "       g.libelle AS genre " +
			  "FROM Livres l " + 
			  "LEFT JOIN Genre g ON g.id = l.genre_id ";

			private static final String SELECT_ALL_SQL        = BASE_SELECT + "ORDER BY l.id";
			private static final String SELECT_TITRE_LIKE_SQL = BASE_SELECT + "WHERE l.titre LIKE ? ORDER BY l.id";
			private static final String SELECT_BY_ID_SQL      = BASE_SELECT + "WHERE l.id = ?";

			private static final String UPDATE_SQL =
			  "UPDATE Livres SET titre=?, auteur=?, isbn=?, annee_publication=?, genre_id=? WHERE id=?";

			private static final String DELETE_SQL = "DELETE FROM Livres WHERE id=?";

			private Livre map(ResultSet rs) throws SQLException {
			    Livre l = new Livre();
			    l.setId(rs.getInt("id"));
			    l.setTitre(rs.getString("titre"));
			    l.setAuteur(rs.getString("auteur")); 
			    l.setIsbn(rs.getString("isbn"));
			    l.setAnneePublication(rs.getInt("annee_publication"));

			    // FK (peut être null)
			    Object gid = rs.getObject("genre_id");
			    l.setGenreId(gid == null ? null : ((Number) gid).intValue());

			    // Libellé via la jointure
			    l.setGenre(rs.getString("genre"));
			    return l;
			}

    @Override
    public int ajouter(Livre livre) throws Exception {
        try (Connection cn = ConnexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getIsbn());
            ps.setInt(4, livre.getAnneePublication());
            if (livre.getGenreId() == null) {   
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, livre.getGenreId().intValue());
            }
            

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Insertion échouée, aucune ligne affectée.");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
                throw new SQLException("Insertion OK mais ID généré introuvable.");
            }
        } catch (SQLException e) {
            // gestion simple de l’unicité ISBN
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("unique"))
                throw new SQLException("ISBN déjà existant.", e);
            throw e;
        }
    }

    @Override
    public List<Livre> trouverTous() throws Exception {
        List<Livre> res = new ArrayList<Livre>();
        try (Connection cn = ConnexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) res.add(map(rs));
        }
        return res;
    }

    @Override
    public List<Livre> trouverParTitreLike(String motif) throws Exception {
        List<Livre> res = new ArrayList<Livre>();
        try (Connection cn = ConnexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_TITRE_LIKE_SQL)) {

            ps.setString(1,"%" + motif + "%"); // ex: "%Java%"
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) res.add(map(rs));
            }
        }
        return res;
    }

    @Override
    public boolean modifier(int id, Livre livre) throws Exception {
        try (Connection cn = ConnexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setString(3, livre.getIsbn());
            ps.setInt(4, livre.getAnneePublication());
            if (livre.getGenreId() == null) {
                ps.setNull(5, Types.INTEGER);
            } else {
                ps.setInt(5, livre.getGenreId());
            }
            ps.setInt(6, id);

            return ps.executeUpdate() == 1;   
        }
    }

    @Override
    public boolean supprimer(int id) throws Exception {
        try (Connection cn = ConnexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);  
            return ps.executeUpdate() == 1;
        }
    }
    
    @Override
	public Livre trouverParId(int id) throws Exception {
	    try (Connection cn = ConnexionDB.getConnection();
	         PreparedStatement ps = cn.prepareStatement(SELECT_BY_ID_SQL)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            return rs.next() ? map(rs) : null;
	        }
	    }
	}
    
    
}
