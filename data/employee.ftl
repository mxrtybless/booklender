<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${employee.name}</title>
</head>
<body>

<p>
    <a href="/employees">Back to employees</a> |
    <a href="/books">Books</a> |
    <a href="/login">Login</a> |
    <a href="/register">Register</a>
</p>

<h1>${employee.name}</h1>

<p>
    <b>ID:</b>
    ${employee.id}
</p>

<p>
    <b>Email:</b>
    ${employee.email}
</p>

<h2>Current books</h2>

<#if employee.currentBooks?size == 0>
    <p>No current books.</p>
<#else>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Status</th>
        </tr>

        <#list employee.currentBooks as book>
            <tr>
                <td>${book.id}</td>
                <td>
                    <a href="/book?id=${book.id}">${book.title}</a>
                </td>
                <td>${book.author}</td>
                <td>${book.statusText}</td>
            </tr>
        </#list>
    </table>
</#if>

<h2>Books history</h2>

<#if employee.historyBooks?size == 0>
    <p>No books in history.</p>
<#else>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Author</th>
            <th>Status</th>
        </tr>

        <#list employee.historyBooks as book>
            <tr>
                <td>${book.id}</td>
                <td>
                    <a href="/book?id=${book.id}">${book.title}</a>
                </td>
                <td>${book.author}</td>
                <td>${book.statusText}</td>
            </tr>
        </#list>
    </table>
</#if>

</body>
</html>