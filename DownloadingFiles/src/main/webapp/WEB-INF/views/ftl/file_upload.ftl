<script src="/webjars/jquery/3.4.1/jquery.js"></script>
<#--<script-->
            <#--src="https://code.jquery.com/jquery-3.4.1.js"-->
            <#--integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="-->
            <#--crossorigin="anonymous"></script>-->
<script>
    function sendFile() {
        // данные для отправки
        let formData = new FormData();
        // забрал файл из input
        let files = ($('#file'))[0]['files'];
        // добавляю файл в formData
        [].forEach.call(files, function (file, i, files) {
            formData.append("file", file);
            formData.append("email",($('#email')))
        });

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
                    alert('No today')
                });
    }
</script>
<div>
    <input type="file" id="file" name="file" placeholder="Name file..."/>
    <input type="email" id="email" name="email" placeholder="email"/>
    <button onclick="sendFile()">
        Download
    </button>
    <input type="hidden" id="file_hidden">
    <div class="filename"></div>
</div>