package ru.javalab.mongodb2.creator;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.io.File;
import java.io.IOException;

public abstract class DocumentCreator {
    protected PdfDocument pdfDocument;

    public void createDocument() throws IOException {
        String filenameInput = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1)
                + "templates/" + getTemplateName() + ".pdf";
        String filenameOutput = Const.DOCUMENT_STORAGE + getTemplateName() + "_" + getGeneratedFileName() + ".pdf";
        pdfDocument = new PdfDocument(new PdfReader(filenameInput), new PdfWriter(new File(filenameOutput)));
        fillDocument();
        Document document = new Document(pdfDocument);
        document.close();
        pdfDocument.close();
    }

    protected abstract void fillDocument();

    public abstract String getTemplateName();

    public abstract String getGeneratedFileName();
}