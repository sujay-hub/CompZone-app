package com.project1.ecommerce.compstore.services;

import java.io.ByteArrayInputStream;

import com.project1.ecommerce.compstore.entities.Orders;

public interface PDFService {
	public ByteArrayInputStream generateInvoice(Orders order) throws Exception;

}
