<!DOCTYPE html>
<html>
<head>
    <title>Employees</title>
</head>
<body>

<h1>Employees</h1>

<table border="1">

    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Current books</th>
    </tr>

    <#list employees as employee>

        <tr>

            <td>${employee.id}</td>

            <td>
                <a href="/employee?id=${employee.id}">
                    ${employee.name}
                </a>
            </td>

            <td>${employee.currentBooks?size}</td>

        </tr>

    </#list>

</table>

<br>

<a href="/books">Books</a>

</body>
</html>