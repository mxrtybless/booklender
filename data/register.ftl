<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="/css/forms.css">
</head>
<body>

<main>
    <form action="/register" method="post">
        <fieldset>
            <div class="legend">
                <p>Employee registration</p>
            </div>

            <#if message??>
                <p>
                    <b>${message}</b>
                </p>
            </#if>

            <div class="form-element">
                <label for="email">Email</label>
                <input
                        type="email"
                        name="email"
                        id="email"
                        placeholder="employee email"
                        value="${email!""}"
                        required
                        autofocus
                >
            </div>

            <div class="form-element">
                <label for="name">Name</label>
                <input
                        type="text"
                        name="name"
                        id="name"
                        placeholder="employee name"
                        value="${name!""}"
                        required
                >
            </div>

            <div class="form-element">
                <label for="password">Password</label>
                <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="password"
                        required
                >
            </div>

            <div class="hr-line">
                <span class="details">create account</span>
            </div>

            <div class="form-element">
                <button class="register-button" type="submit">Register</button>
            </div>
        </fieldset>
    </form>

    <p style="text-align: center;">
        <a href="/login">Login</a> |
        <a href="/employees">Employees</a> |
        <a href="/books">Books</a>
    </p>
</main>

</body>
</html>