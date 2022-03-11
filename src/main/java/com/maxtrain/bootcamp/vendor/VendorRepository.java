package com.maxtrain.bootcamp.vendor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.maxtrain.bootcamp.invoice.Invoice;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {
	@Query("Select v from Vendor v where v.code = ?1")
	Vendor findCode(String code);
	
}
