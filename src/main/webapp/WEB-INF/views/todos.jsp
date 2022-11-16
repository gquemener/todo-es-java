<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Todos</title>
</head>
<body>
    <h1>Todos</h1>
    <ul>
        <c:forEach items="${todos}" var="todo">
            <li>
                <c:if test="${empty todo.duration}"><a href="/close/${todo.id}">Close</a> - </c:if>
                <c:if test="${!empty todo.duration}"><strike></c:if>
                ${todo.description}<c:if test="${!empty todo.duration}"></strike> (Closed in ${todo.duration})</c:if>
            </li>
        </c:forEach>
    </ul>

    <hr />
    <h2>Create Todo</h2>
    <form action="/todos" method="post">
        <label for="description">Description: </label>
        <input id="description" name="description" type="text" required tabindex="1" />
        <input type="submit" value="Create" />
    </form>
</body>
</html>