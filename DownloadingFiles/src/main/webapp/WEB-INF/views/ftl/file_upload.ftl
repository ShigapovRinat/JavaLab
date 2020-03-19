<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="/webjars/jquery/3.4.1/jquery.js"></script>
    <script>
        function sendFile() {
            // данные для отправки
            let formData = new FormData();
            // забрал файл из input
            let files = ($('#file'))[0]['files'];
            let email = document.getElementById('email').value;
            // добавляю файл в formData
            [].forEach.call(files, function (file, i, files) {
                formData.append("file", file);
            });
            formData.append('email', email);
            $.ajax({
                type: "POST",
                url: "http://localhost:8080/files",
                data: formData,
                processData: false,
                contentType: false
            })
                    .done(function (response) {
                        alert(response)
                    })
                    .fail(function () {
                        alert('Error')
                    });
        }
    </script>
    <title>Document</title>
</head>
<body>
<div>
    <input type="file" id="file" name="file" placeholder="Name file..."/>
    <input type="email" id="email" name="email" placeholder="email"/>
    <button onclick="sendFile()">
        Download
    </button>
    <input type="hidden" id="file_hidden">
    <div class="filename"></div>
</div>
</body>
</html>


