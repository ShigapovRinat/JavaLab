<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script>
        function changeValue() {
            var name = document.getElementById("name_input").value;
            document.getElementsByTagName('span')[0].innerText = "" + name;
        }
    </script>
</head>
<body>

<span id="name">1</span>
<br>

<input type="text" id="name_input">
<button onclick="changeValue()">Change name</button>

<form action="/queue" method="post">
    <input type="text" name="queueName" placeholder="Queue name">
    <input type="submit" value="Send">
</form>
</body>
</html>