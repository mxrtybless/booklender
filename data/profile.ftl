<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee profile</title>
    <link rel="stylesheet" href="/css/freemarker.css">
</head>
<body>

<div class="container">
    <h1>Employee profile</h1>

    <#if authorized>
        <p><b>You are logged in.</b></p>
        <p>
            <a href="/logout">Logout</a>
        </p>

        <#if messageCode?? && messageCode != "">
            <#if messageCode == "bookTaken">
                <p><b>Book was successfully taken.</b></p>
            <#elseif messageCode == "bookNotTaken">
                <p><b>Book was not taken. Maybe it is already issued or you already have two books.</b></p>
            <#elseif messageCode == "bookReturned">
                <p><b>Book was successfully returned.</b></p>
            <#elseif messageCode == "bookNotReturned">
                <p><b>Book was not returned.</b></p>
            </#if>
        </#if>

        <p>
            <b>ID:</b>
            ${employee.id}
        </p>

        <p>
            <b>Email:</b>
            ${employee.email}
        </p>

        <p>
            <b>Name:</b>
            ${employee.name}
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
                    <th>Action</th>
                </tr>

                <#list employee.currentBooks as book>
                    <tr>
                        <td>${book.id}</td>
                        <td>
                            <a href="/book?id=${book.id}">${book.title}</a>
                        </td>
                        <td>${book.author}</td>
                        <td>
                            <form method="post" action="/return">
                                <input type="hidden" name="bookId" value="${book.id}">
                                <button type="submit">Return</button>
                            </form>
                        </td>
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
                </tr>

                <#list employee.historyBooks as book>
                    <tr>
                        <td>${book.id}</td>
                        <td>
                            <a href="/book?id=${book.id}">${book.title}</a>
                        </td>
                        <td>${book.author}</td>
                    </tr>
                </#list>
            </table>
        </#if>

        <h2>Available books</h2>

        <#if !canTakeBooks>
            <p>You already have two books. Return one book before taking another one.</p>
        </#if>

        <#if availableBooks?size == 0>
            <p>No available books.</p>
        <#else>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Action</th>
                </tr>

                <#list availableBooks as book>
                    <tr>
                        <td>${book.id}</td>
                        <td>
                            <a href="/book?id=${book.id}">${book.title}</a>
                        </td>
                        <td>${book.author}</td>
                        <td>
                            <#if canTakeBooks>
                                <form method="post" action="/issue">
                                    <input type="hidden" name="bookId" value="${book.id}">
                                    <button type="submit">Take</button>
                                </form>
                            <#else>
                                Not allowed
                            </#if>
                        </td>
                    </tr>
                </#list>
            </table>
        </#if>
    <#else>
        <p>You are not logged in.</p>
        <p>
            <a href="/login">Login</a> |
            <a href="/register">Register</a>
        </p>
    </#if>

    <p>
        <a href="/">Main page</a> |
        <a href="/books">Books</a> |
        <a href="/employees">Employees</a> |
        <a href="/login">Login</a> |
        <a href="/register">Register</a>
    </p>
</div>

</body>
</html>