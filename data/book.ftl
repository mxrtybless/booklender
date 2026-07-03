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
    <a href="/profile">Profile</a> |
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

<#if authorizedEmployee??>
    <h2>Action</h2>

    <#if canTakeBook>
        <form method="post" action="/issue">
            <input type="hidden" name="bookId" value="${book.id}">
            <button type="submit">Take this book</button>
        </form>
    <#elseif canReturnBook>
        <form method="post" action="/return">
            <input type="hidden" name="bookId" value="${book.id}">
            <button type="submit">Return this book</button>
        </form>
    <#else>
        <p>No available action for this book.</p>
    </#if>
<#else>
    <p>
        <a href="/login">Login</a> to take or return books.
    </p>
</#if>

</body>
</html>