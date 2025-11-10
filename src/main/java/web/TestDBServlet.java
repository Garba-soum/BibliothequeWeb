package web;

import java.io.IOException;
import java.sql.Connection;

import dao.ConnexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/testDB")
public class TestDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        Connection conn = ConnexionDB.getConnection();
        if (conn != null) {
            resp.getWriter().println("Connexion SQL Server établie ✅");
        } else {
            resp.getWriter().println("Échec de la connexion ❌");
        }
    }
}
