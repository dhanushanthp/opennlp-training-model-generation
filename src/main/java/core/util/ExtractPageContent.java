package core.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
/**
 * 
 * @author Dhanushanth
 * Read the PDF file and pass as a pure text.
 *
 */
public class ExtractPageContent {

	public static final String PREFACE = "/home/dhanu/Desktop/0136114520-Source-Full_Good.pdf";
	public static final String RESULT = "/home/dhanu/Desktop/sampleOutput.txt";

	public void parsePdf(String pdf, String txt) throws IOException {
		PdfReader reader = new PdfReader(pdf);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		PrintWriter out = new PrintWriter(new FileOutputStream(txt));
		TextExtractionStrategy strategy;
		for (int i = 1; i <= reader.getNumberOfPages(); i++) {
			strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
			out.println(strategy.getResultantText());
		}
		out.flush();
		out.close();
		reader.close();
	}
	
	public static void main(String[] args) throws IOException {
		new ExtractPageContent().parsePdf(PREFACE, RESULT);
	}
}