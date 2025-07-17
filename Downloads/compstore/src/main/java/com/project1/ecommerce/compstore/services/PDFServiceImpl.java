package com.project1.ecommerce.compstore.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project1.ecommerce.compstore.entities.OrderItems;
import com.project1.ecommerce.compstore.entities.Orders;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PDFServiceImpl implements PDFService {

	@Override
	public ByteArrayInputStream generateInvoice(Orders order) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		Document doc = new Document();
		
		PdfWriter.getInstance(doc, out);
		
		doc.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
		Paragraph title = new Paragraph("Order invoice", font);
		title.setAlignment(Element.ALIGN_CENTER);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = order.getCreatedAt().format(formatter);

		
		doc.add(title);
		
		doc.add(new Paragraph(" "));
		doc.add(new Paragraph("Order ID: "+ order.getOrderId()));
		doc.add(new Paragraph("Date: " + formattedDate));
		//doc.add(new Paragraph("Customer: "+ order.getUsers().getUserName()));
		doc.add(new Paragraph("email: "+ order.getUsers().getEmail()));
		doc.add(new Paragraph(" "));
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new int[] {3,1,1});
		
		String[] headers = {"Product", "Quantity", "Price"};
		
		for(int i = 0; i < headers.length; i++) {
			PdfPCell header = new PdfPCell();
			Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			header.setPhrase(new Phrase(headers[i], headFont));
			table.addCell(header);
		}
		
		List<OrderItems> items = order.getItems();
		for(int i = 0; i < items.size(); i++) {
			OrderItems item = items.get(i);
			table.addCell(item.getProducts().getProductName());
			table.addCell(String.valueOf(item.getQuantity()));
			table.addCell("₹"+ item.getProducts().getPrice());
		}
		
		doc.add(table);
		
		doc.add(new Paragraph(" "));
		doc.add(new Paragraph("Total: ₹"+ order.getTotalAmount(), font));
		
		doc.close();
				
		return new ByteArrayInputStream(out.toByteArray());
	}

}
