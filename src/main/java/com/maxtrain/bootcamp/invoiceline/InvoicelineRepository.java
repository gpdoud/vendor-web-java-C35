package com.maxtrain.bootcamp.invoiceline;

import org.springframework.data.repository.CrudRepository;

import com.maxtrain.bootcamp.invoice.Invoice;

public interface InvoicelineRepository extends CrudRepository<Invoiceline, Integer>{
	Iterable<Invoiceline> findByInvoice(Invoice invoice);
}
