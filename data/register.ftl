<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>

    <link rel="stylesheet" href="/css/forms.css">
</head>
<body>

<h1>Employee registration</h1>

<form action="/register" method="post">

    <label>Email</label><br>
    <input
            type="email"
            name="email"
            required
    >
    <br><br>

    <label>Name</label><br>
    <input
            type="text"
            name="name"
            required
    >
    <br><br>

    <label>Password</label><br>
    <input
            type="password"
            name="password"
            required
    >
    <br><br>

    <button type="submit">
        Register
    </button>

</form>

<br>

<a href="/login">Login</a>

</body>
</html>