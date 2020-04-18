<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, person-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>login</title>
</head>
<body>
<h1>Регистрация</h1>
<div>
    <form action="/signUp" method="post">
        <input name="login" placeholder="login">
        <input type="password" name="password" placeholder="Пароль">
        <br>
        <input type="submit" value="Войти">
    </form>
    <#if exception??>
        ${exception.message}
    </#if>
</div>
</body>
</html>