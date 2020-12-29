package ru.javalab.mongodb2.creator;

import com.itextpdf.forms.PdfAcroForm;
import ru.javalab.mongodb2.model.User;

import java.util.UUID;

public class VacationDocumentCreator extends DocumentCreator {
    private User user;

    public VacationDocumentCreator(User user) {
        this.user = user;
    }

    @Override
    protected void fillDocument() {
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDocument, true);
        form.getField("full_name").setReadOnly(true);
        form.getField("full_name").setValue(user.getFirstName() + " " + user.getLastName());

    }

    @Override
    public String getTemplateName() {
        return "vacation";
    }

    @Override
    public String getGeneratedFileName() {
        return user.getFirstName() + "_" + user.getLastName() + "_" + UUID.randomUUID().toString();
    }
}