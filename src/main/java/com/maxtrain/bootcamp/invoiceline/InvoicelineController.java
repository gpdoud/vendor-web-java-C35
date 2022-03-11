package com.maxtrain.bootcamp.invoiceline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.bootcamp.invoice.InvoiceRepository;
import com.maxtrain.bootcamp.product.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/invoicelines")
public class InvoicelineController {

	@Autowired
	private InvoicelineRepository invlRepo;
	@Autowired
	private InvoiceRepository invRepo;
	@Autowired
	private ProductRepository prdRepo;
	
	@SuppressWarnings("rawtypes")
	private ResponseEntity recalcInvoice(int invoiceId) {
		var inv = invRepo.findById(invoiceId);
		if(inv.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		var invoiceTotal = 0;
		var invLines = invlRepo.findByInvoice(inv.get());
		for(var invl : invLines) {
			invoiceTotal += invl.getProduct().getPrice() * invl.getQuantity();
		}
		inv.get().setTotal(invoiceTotal);
		invRepo.save(inv.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Invoiceline>> getInvoicelines() {
		var invoicelines = invlRepo.findAll();
		return new ResponseEntity<Iterable<Invoiceline>>(invoicelines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Invoiceline> getInvoiceline(@PathVariable int id) {
		var invoiceline = invlRepo.findById(id);
		if(invoiceline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Invoiceline>(invoiceline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Invoiceline> postInvoiceline(@RequestBody Invoiceline invoiceline) throws Exception {
		if(invoiceline == null || invoiceline.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var invl = invlRepo.save(invoiceline);
		var responseEntity = this.recalcInvoice(invoiceline.getInvoice().getId());
		if(responseEntity.getStatusCodeValue() != 200) {
			throw new Exception("recalcInvoice failed!: status: " + responseEntity.getStatusCodeValue());
		}
		return new ResponseEntity<Invoiceline>(invl, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putInvoiceline(@PathVariable int id, @RequestBody Invoiceline invoiceline) throws Exception  {
		if(invoiceline == null || invoiceline.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var invl = invlRepo.findById(invoiceline.getId());
		if(invl.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invlRepo.save(invoiceline);
		var responseEntity = this.recalcInvoice(invoiceline.getInvoice().getId());
		if(responseEntity.getStatusCodeValue() != 200) {
			throw new Exception("recalcInvoice failed!: status: " + responseEntity.getStatusCodeValue());
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteInvoiceline(@PathVariable int id) throws Exception {
		var invoiceline = invlRepo.findById(id);
		if(invoiceline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invlRepo.delete(invoiceline.get());
		var responseEntity = this.recalcInvoice(invoiceline.get().getInvoice().getId());
		if(responseEntity.getStatusCodeValue() != 200) {
			throw new Exception("recalcInvoice failed!: status: " + responseEntity.getStatusCodeValue());
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
