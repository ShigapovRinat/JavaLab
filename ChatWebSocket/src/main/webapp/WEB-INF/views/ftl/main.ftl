<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, person-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Chats</title>
</head>
<body>
<div>
    <#list chatList as chat>
        ${chat.name}
            <form action="/inviteChat" method="post">
                <input type="hidden" name="id" value="${chat.id}">
                <input type="password" name="password" placeholder="password" ">
                <input type="submit" value="Присоединиться">
            </form>
        <br>
    </#list>
    <form action="/createChat" method="post">
        <input name="name" placeholder="Название">
        <input type="password" name="password" placeholder="Пароль">
        <input type="submit" value="Создать чат">
    </form>
    <#if selfChatList??>
        Созданные мной чаты:
        <br>
        <#list selfChatList as chat>
            ${chat.name}
        <br>
        </#list>
    </#if>

</div>
</body>
</html>