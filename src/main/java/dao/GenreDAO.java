package dao;

import java.util.List;
import model.Genre;


public interface GenreDAO {
	
	List<Genre> findAll() throws Exception;
    Genre findById(int id) throws Exception;
}
