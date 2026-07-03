<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${book.title}</title>
</head>
<body>

<p>
    <a href="/books">Back to books</a> |
    <a href="/employees">Employees</a> |
    <a href="/login">Login</a> |
    <a href="/register">Register</a>
</p>

<h1>${book.title}</h1>

<p>
    <img src="${book.image}" alt="${book.title}" width="220">
</p>

<p>
    <b>ID:</b>
    ${book.id}
</p>

<p>
    <b>Author:</b>
    ${book.author}
</p>

<p>
    <b>Description:</b>
    ${book.description}
</p>

<p>
    <b>Status:</b>
    ${book.statusText}
</p>

<p>
    <b>Issued to:</b>
    <#if employee??>
        <a href="/employee?id=${employee.id}">${employee.name}</a>
    <#else>
        Nobody
    </#if>
</p>

</body>
</html>