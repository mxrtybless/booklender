<!DOCTYPE html>
<html>
<head>
    <title>Books</title>
</head>
<body>

<h1>Books list</h1>

<table border="1">

    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Status</th>
    </tr>

    <#list books as book>

        <tr>

            <td>
                <a href="/book?id=${book.id}">
                    ${book.title}
                </a>
            </td>

            <td>${book.author}</td>

            <td>
                ${book.issued?string("Issued", "Available")}
            </td>

        </tr>

    </#list>

</table>

</body>
</html>