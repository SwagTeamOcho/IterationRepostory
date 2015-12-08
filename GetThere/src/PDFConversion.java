package src;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JPanel;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFConversion {
	
	public PDFConversion() {
		convert();
	}
	
	public void convert() {
		try {
			File file = new File("Directions.pdf");
	        FileOutputStream pdfFileout = new FileOutputStream(file);
	        Document doc = new Document();
	        PdfWriter.getInstance(doc, pdfFileout);
	
	        doc.addAuthor("GetThere");
	        doc.addTitle("This is title");
	        doc.open();
	
	        Paragraph para1 = new Paragraph();
	        para1.add("Here are your direction maps");
	
	        doc.add(para1);
	         
	        //adding a local image and aligned RIGHT
	        Image image = Image.getInstance("Screen.png");
	        image.scaleAbsolute(200, 150);
	        doc.add(image);
	
	        doc.close();
	        pdfFileout.close();
	
	        System.out.println("Success!");
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
}
