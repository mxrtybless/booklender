<!DOCTYPE html>
<html>
<head>
    <title>Books</title>
</head>
<body>

<h1>Books list</h1>

<#list books as book>
    <div>
        <h3>${book.title}</h3>
        <p>${book.author}</p>

        <#if book.issued>
            <p style="color:red;">Issued</p>
        <#else>
            <p style="color:green;">Available</p>
        </#if>
    </div>
</#list>

</body>
</html>