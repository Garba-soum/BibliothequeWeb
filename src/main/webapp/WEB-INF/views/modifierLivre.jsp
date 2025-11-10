<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Modifier un livre</title>
  <style>
    body{font-family:Arial;margin:24px} label{display:block;margin-top:10px}
    input,select{width:420px;padding:6px}
    button{margin-top:14px;padding:8px 16px}
  </style>
</head>
<body>
  <h1>Modifier un livre</h1>

  <form action="${pageContext.request.contextPath}/modifier-livre" method="post">
    <input type="hidden" name="id" value="${livre.id}"/>

    <label>Titre :</label>
    <input type="text" name="titre" value="${livre.titre}" required>

    <label>Auteur :</label>
    <input type="text" name="auteur" value="${livre.auteur}" required>

    <label>ISBN :</label>
    <input type="text" name="isbn" value="${livre.isbn}" required>

    <label>Année :</label>
    <input type="number" name="annee" value="${livre.anneePublication}" required>

    <label>Genre :</label>
    <select name="genreId">
      <option value="">— Sélectionner —</option>
      <c:forEach var="g" items="${genres}">
        <option value="${g.id}"
          <c:if test="${livre.genreId == g.id}">selected</c:if>>
          ${g.libelle}
        </option>
      </c:forEach>
    </select>

    <button type="submit">Enregistrer</button>
    <a href="${pageContext.request.contextPath}/livres">Annuler</a>
  </form>
</body>
</html>
