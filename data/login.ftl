<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login form</title>
    <link rel="stylesheet" href="/css/forms.css">
</head>
<body>

<main>
    <form action="/login" method="post">
        <fieldset>
            <div class="legend">
                <p>Employee login</p>
                <img src="/images/1.jpg" alt="book" style="display:block; width: 200px; height: 200px;">
            </div>

            <#if error??>
                <p>
                    <b>${error}</b>
                </p>
            </#if>

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
                        placeholder="your email"
                        value="${email!""}"
                        required
                        autofocus
                >
            </div>

            <div class="form-element">
                <label for="password">Password</label>
                <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="your password"
                        required
                >
            </div>

            <div class="hr-line">
                <span class="details">one more step to go</span>
            </div>

            <div class="form-element">
                <button class="register-button" type="submit">Login</button>
            </div>
        </fieldset>
    </form>

    <p style="text-align: center;">
        <a href="/register">Register</a> |
        <a href="/employees">Employees</a> |
        <a href="/books">Books</a>
    </p>
</main>

</body>
</html>