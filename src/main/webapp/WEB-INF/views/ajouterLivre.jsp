<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ajouter un livre</title>
<style>
  body { font-family: Arial, sans-serif; margin: 24px; }
  label { display:block; margin-top:10px; }
  input, select { width:300px; padding:5px; }
  button { margin-top:15px; padding:8px 16px; }
  .msg { color:red; margin-top:10px; }
</style>
</head>   
<body>
  <h1>Ajouter un nouveau livre</h1>

  <form action="${pageContext.request.contextPath}/ajouter-livre" method="post">
    <label for="titre">Titre :</label>
    <input id="titre" type="text" name="titre" value="${livre.titre}" required>

    <label for="auteur">Auteur :</label>
    <input id="auteur" type="text" name="auteur" value="${livre.auteur}" required>

    <label for="isbn">ISBN :</label>
    <input id="isbn" type="text" name="isbn" value="${livre.isbn}" required>

    <label for="annee">Année :</label>
    <input id="annee" type="number" name="annee" value="${livre.anneePublication}">

    <label for="genreId">Genre :</label>
    <select id="genreId" name="genreId" required>
      <option value="">— Sélectionner —</option>
      <c:forEach var="g" items="${genres}">
        <option value="${g.id}"
          <c:if test="${livre.genreId == g.id || param.genreId == g.id}">selected</c:if>>
          ${g.libelle}
        </option>
      </c:forEach>
    </select>

    <button type="submit">Ajouter</button>
  </form>

  <c:if test="${not empty error}">
    <p class="msg">${error}</p>
  </c:if>

  <p><a href="${pageContext.request.contextPath}/livres">⬅ Retour à la liste</a></p>
</body>
</html>
