package com.maxtrain.bootcamp.vendor;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.invoice.InvoiceRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	
	@Autowired
	private VendorRepository vndRepo;
//	@Autowired
//	private InvoiceRepository invRepo;
//	
//	@SuppressWarnings("rawtypes")
//	@PutMapping("recalc/{vendorId}")
//	public ResponseEntity recalcVendorTotal(@PathVariable int vendorId) {
//		var optVendor = vndRepo.findById(vendorId);
//		if(optVendor.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		var vendor = optVendor.get();
//		var invoices = invRepo.findInvoicesByVendorId(vendorId);
//		var invoiceTotal = 0;
//		for(var invoice : invoices) {
//			invoiceTotal += invoice.getTotal();
//		}
//		vendor.setTotal(invoiceTotal);
//		vndRepo.save(vendor);
//		
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> getVendors() {
		var vendors = vndRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> getVendor(@PathVariable int id) {
		var vendor = vndRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	@GetMapping("code/{code}")
	public ResponseEntity<Vendor> getVendorByCode(@PathVariable String code) {
		var vend = vndRepo.findCode(code);
		if(vend == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vend, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> postVendor(@RequestBody Vendor vendor) {
		if(vendor == null || vendor.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var vend = vndRepo.save(vendor);
		return new ResponseEntity<Vendor>(vend, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putCustomer(@PathVariable int id, @RequestBody Vendor vendor) {
		if(vendor == null || vendor.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var vend = vndRepo.findById(vendor.getId());
		if(vend.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vndRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteVendor(@PathVariable int id) {
		var vendor = vndRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vndRepo.delete(vendor.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
