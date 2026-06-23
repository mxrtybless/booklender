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

<h2>Current books</h2>

<ul>
    <#list employee.currentBooks as book>
        <li>${book.title}</li>
    </#list>
</ul>

<h2>Books history</h2>

<ul>
    <#list employee.historyBooks as book>
        <li>${book.title}</li>
    </#list>
</ul>

<br>

<a href="/employees">Back to employees</a>

</body>
</html>