<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employees</title>
</head>
<body>

<h1>Employees</h1>

<p>
    <a href="/">Main page</a> |
    <a href="/books">Books</a> |
    <a href="/login">Login</a> |
    <a href="/register">Register</a>
</p>

<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th>Current books</th>
        <th>Books count</th>
    </tr>

    <#list employees as employee>
        <tr>
            <td>${employee.id}</td>
            <td>
                <a href="/employee?id=${employee.id}">
                    ${employee.name}
                </a>
            </td>
            <td>${employee.email}</td>
            <td>
                <#if employee.currentBooks?size == 0>
                    No current books
                <#else>
                    <ul>
                        <#list employee.currentBooks as book>
                            <li>
                                <a href="/book?id=${book.id}">${book.title}</a>
                            </li>
                        </#list>
                    </ul>
                </#if>
            </td>
            <td>${employee.currentBooksCount}</td>
        </tr>
    </#list>
</table>

</body>
</html>