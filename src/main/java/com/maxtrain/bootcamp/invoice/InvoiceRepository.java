package com.maxtrain.bootcamp.invoice;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {

//	@Query("Select i from Invoice i where i.vendorId = ?1")
//	List<Invoice> findInvoicesByVendorId(int id);
}
