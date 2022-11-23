<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Todos - Log</title>
</head>
<body>
    <h1>Log</h1>
    <ul>
        <c:forEach items="${entries}" var="entry">
            <li>
                ${entry.date} - ${entry.message}
            </li>
        </c:forEach>
    </ul>

    <hr />
    <a href="/">Back</a>
</body>
</html>