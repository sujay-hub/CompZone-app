package com.project1.ecommerce.compstore.controllers;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.ecommerce.compstore.entities.Orders;
//import com.project1.ecommerce.compstore.services.InvoiceService;
import com.project1.ecommerce.compstore.services.OrdersService;
import com.project1.ecommerce.compstore.services.PDFService;

@RestController
@RequestMapping("/orders/{orderId}/invoice")
public class PDFController {
	
	@Autowired
	OrdersService ordersService;
	@Autowired
	PDFService pdfService;

	@GetMapping
	public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Integer orderId){
		try {
			Orders order = ordersService.getOrderById(orderId);//create orderById implementation
			//You should import your own Order entity/model class — the one you created for storing and handling order data in your database, not from Razorpay or Spring.
			ByteArrayInputStream bis = pdfService.generateInvoice(order);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition","inline; filename=invoice_"+orderId + ".pdf");
			
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		}
		catch(Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body(null);
			e.printStackTrace(); // 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
	}
}
