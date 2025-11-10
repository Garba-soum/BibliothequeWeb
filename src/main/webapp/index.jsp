<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Accueil - Bibliothèque</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      text-align: center;
      margin-top: 80px;
      background: #f5f5f5;
    }
    h1 { color: #333; }
    a {
      display: inline-block;
      margin: 12px;
      padding: 10px 18px;
      text-decoration: none;
      color: white;
      background-color: #007bff;
      border-radius: 6px;
      transition: 0.3s;
    }
    a:hover { background-color: #0056b3; }
  </style>
</head>
<body>
  <h1>Bienvenue dans la Bibliothèque</h1>
  <a href="${pageContext.request.contextPath}/livres">📚 Voir la liste des livres</a>
</body>
</html>
