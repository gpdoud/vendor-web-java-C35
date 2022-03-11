package com.maxtrain.bootcamp.vendor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {
	@Query("Select v from Vendor v where v.code = ?1")
	Vendor findCode(String code);
}
