package consumer;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;

public class CreatorDocument {

    public static void createPdf(String dest, String text) throws IOException {

        PdfFont freeUnicode =
                PdfFontFactory.createFont("src/main/resources/fonts/FreeSans.ttf",
                        "Cp1251", true);
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph().setFont(freeUnicode).add(text));
        document.close();
    }

}
