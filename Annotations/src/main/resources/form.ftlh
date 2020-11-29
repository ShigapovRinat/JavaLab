<form method="${method}" action="/${action}">
    <#list htmlInputs as htmlInput>
        <input type="${htmlInput.type}" name="${htmlInput.name}" placeholder="${htmlInput.placeholder}">
    </#list>
</form>