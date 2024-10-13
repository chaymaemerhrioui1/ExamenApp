<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Examen</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Modifier Examen</h2>
        <form action="${pageContext.request.contextPath}/examens/modifier/${examen.id}" method="post" enctype="multipart/form-data">

                        <div class="form-group">
                            <label for="epreuveFilePath">Épreuve</label>
                            <input type="file" class="form-control" id="epreuveFilePath" name="epreuveFilePath">
                        </div>
                        <div class="form-group">
                            <label for="pvFilePath">PV</label>
                            <input type="file" class="form-control" id="pvFilePath" name="pvFilePath">
                        </div>
                        <div class="form-group">
                            <label for="rapportTextuel">Rapport</label>
                            <textarea class="form-control" id="rapportTextuel" name="rapportTextuel" rows="5">${examen.rapportTextuel}</textarea>
                        </div>
            <button type="submit" class="btn btn-primary">Enregistrer</button>
            <a href="${pageContext.request.contextPath}/examens" class="btn btn-secondary">Annuler</a>
        </form>
    </div>
</body>
</html>
