<!DOCTYPE html>
<html>
<head>
    <title>${book.title}</title>
</head>
<body>

<h1>${book.title}</h1>

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
    ${book.issued?string("Issued", "Available")}
</p>

<p>
    <b>Employee ID:</b>
    ${book.issuedTo!"Nobody"}
</p>

<p>
    <b>Image:</b>
    ${book.image}
</p>

<br>

<a href="/books">Back to books</a>

</body>
</html>