package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Genre;

public class GenreDAOImpl implements GenreDAO {

    private static final String SELECT_ALL =
        "SELECT id, libelle FROM GENRE ORDER BY libelle";
    private static final String SELECT_BY_ID =
        "SELECT id, libelle FROM GENRE WHERE id=?";

    @Override
    public List<Genre> findAll() throws Exception {
        List<Genre> list = new ArrayList<Genre>();
        Connection cnx = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            cnx = ConnexionDB.getConnection();
            ps = cnx.prepareStatement(SELECT_ALL);
            rs = ps.executeQuery();
            while (rs.next()) {
            	list.add(new Genre(rs.getInt("id"), rs.getString("libelle")));
            }
        } finally {
            if (rs!=null) try { rs.close(); } catch(Exception ignored){}
            if (ps!=null) try { ps.close(); } catch(Exception ignored){}
            if (cnx!=null) try { cnx.close(); } catch(Exception ignored){}
        }
        return list;
    }

    @Override
    public Genre findById(int id) throws Exception {
        Connection cnx = null; PreparedStatement ps = null; ResultSet rs = null;
        try {
            cnx = ConnexionDB.getConnection();
            ps = cnx.prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            rs = ps.executeQuery();   
            if (rs.next()) {
                return new Genre(rs.getInt("id"), rs.getString("libelle"));
            } 
            return null;
        } finally {
            if (rs!=null) try { rs.close(); } catch(Exception ignored){}
            if (ps!=null) try { ps.close(); } catch(Exception ignored){}
            if (cnx!=null) try { cnx.close(); } catch(Exception ignored){}
        }   
    }
}
