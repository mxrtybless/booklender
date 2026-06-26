<!DOCTYPE html>
<html>
<head>
    <title>${employee.name}</title>
</head>
<body>

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

<br>

<a href="/employees">Back to employees</a> |
<a href="/login">Login</a> |
<a href="/register">Register</a>

</body>
</html>