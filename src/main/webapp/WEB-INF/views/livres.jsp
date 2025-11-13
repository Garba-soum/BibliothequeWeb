<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Livre" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Liste des livres</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 24px; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ddd; padding: 8px; }
    th { background:#f2f2f2; text-align:left; }
    .msg { margin: 12px 0; color:#b00; }
  </style>
</head>
<body>

  <h1>Liste des livres</h1>

  <!-- Formulaire de recherche -->
  <form method="get" action="${pageContext.request.contextPath}/livres" style="margin: 12px 0;">
    <input type="text" name="q" placeholder="Rechercher par titre"
           value="${param.q}" style="padding:6px; width:250px;">
    <button type="submit">Rechercher</button>
    <a href="${pageContext.request.contextPath}/livres">Réinitialiser</a>
  </form>

  <p><a href="${pageContext.request.contextPath}/ajouter-livre">➕ Ajouter un nouveau livre</a></p>

  <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
  %>
    <p class="msg"><%= error %></p>
  <%
    }
    List<Livre> livres = (List<Livre>) request.getAttribute("livres");
    if (livres == null || livres.isEmpty()) {
  %>
    <p>Aucun livre trouvé.</p>
  <%
    } else {
  %>
    <table>
      <thead>
        <tr>
          <th>ID</th><th>Titre</th><th>Auteur</th><th>ISBN</th><th>Année</th><th>Genre</th><th>Actions</th>
        </tr>
      </thead>
      <tbody>
      <%
        for (Livre l : livres) {
      %>
        <tr>
          <td><%= l.getId() %></td>
          <td><%= l.getTitre() %></td>
          <td><%= l.getAuteur() %></td>
          <td><%= l.getIsbn() %></td>
          <td><%= l.getAnneePublication() %></td>
          <td><%= l.getGenre() %></td>
          <td>
            <a href="<%= request.getContextPath() %>/modifier-livre?id=<%= l.getId() %>">Modifier</a>
            |
            <a href="<%= request.getContextPath() %>/supprimer-livre?id=<%= l.getId() %>"
               onclick="return confirm('Supprimer ce livre ?');">Supprimer</a>
          </td>
        </tr>
      <%
        }
      %>
      </tbody>
    </table>
  <%
    } // fin du else
  %>

  <p style="margin-top:16px">
    <a href="<%= request.getContextPath() %>/">Accueil</a>
  </p>
</body>
</html>
