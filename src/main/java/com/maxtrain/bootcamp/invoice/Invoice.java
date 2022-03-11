package com.maxtrain.bootcamp.invoice;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.maxtrain.bootcamp.invoiceline.Invoiceline;
import com.maxtrain.bootcamp.vendor.Vendor;

@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length=30, nullable=false)
	private String description;
	@Column(length=30, nullable=false)
	private String status;
	@Column(columnDefinition="decimal(9,2) NOT NULL DEFAULT 0.0")
	private double total;
	
	@JsonBackReference
	@ManyToOne(optional=false)
	@JoinColumn(name="vendorId")
	private Vendor vendor;
	
//	@OneToMany(mappedBy="invoice")
//	private List<Invoiceline> invoicelines;
	
	public Invoice() {}

//	public List<Invoiceline> getInvoicelines() {
//		return invoicelines;
//	}
//	
//	public void setInvoicelines(List<Invoiceline> invoicelines) {
//		this.invoicelines = invoicelines;
//	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	
}
