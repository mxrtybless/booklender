<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books</title>
</head>
<body>

<h1>Books list</h1>

<p>
    <a href="/">Main page</a> |
    <a href="/employees">Employees</a> |
    <a href="/login">Login</a> |
    <a href="/register">Register</a>
</p>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Status</th>
        <th>Issued to</th>
    </tr>

    <#list rows as row>
        <#assign book = row.book>

        <tr>
            <td>${book.id}</td>
            <td>
                <a href="/book?id=${book.id}">
                    ${book.title}
                </a>
            </td>
            <td>${book.author}</td>
            <td>${book.statusText}</td>
            <td>
                <#if row.employee??>
                    <a href="/employee?id=${row.employee.id}">
                        ${row.employee.name}
                    </a>
                <#else>
                    Nobody
                </#if>
            </td>
        </tr>
    </#list>
</table>

</body>
</html>