<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee profile</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<div class="container">
    <h1>Employee profile</h1>

    <#if authorized>
        <p><b>Successful login.</b></p>
    <#else>
        <p><b>This page was opened directly, so it shows sample user data.</b></p>
    </#if>

    <p>
        <b>ID:</b>
        ${employee.id}
    </p>

    <p>
        <b>Email:</b>
        ${employee.email}
    </p>

    <p>
        <b>Name:</b>
        ${employee.name}
    </p>

    <h2>Current books</h2>

    <#if employee.currentBooks?size == 0>
        <p>No current books.</p>
    <#else>
        <ul>
            <#list employee.currentBooks as book>
                <li>${book.title}</li>
            </#list>
        </ul>
    </#if>

    <h2>Books history</h2>

    <#if employee.historyBooks?size == 0>
        <p>No books in history.</p>
    <#else>
        <ul>
            <#list employee.historyBooks as book>
                <li>${book.title}</li>
            </#list>
        </ul>
    </#if>

    <p>
        <a href="/login">Login</a> |
        <a href="/register">Register</a> |
        <a href="/employees">Employees</a> |
        <a href="/books">Books</a>
    </p>
</div>

</body>
</html>