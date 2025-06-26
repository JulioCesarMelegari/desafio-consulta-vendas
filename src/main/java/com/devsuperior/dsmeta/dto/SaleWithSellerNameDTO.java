package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.projections.SaleWithSellerNameProjection;

public class SaleWithSellerNameDTO {
	
	private Long id;
	private LocalDate date;
	private Double amount;
	private String name;
	
	public SaleWithSellerNameDTO() {

	}
	
	public SaleWithSellerNameDTO(Long id, LocalDate date, Double amount, String name) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.name = name;
	}
	
	public SaleWithSellerNameDTO(SaleWithSellerNameProjection sale) {
		id = sale.getId();
		date = sale.getDate();
		amount = sale.getAmount();
		name = sale.getName();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SaleWithSellerNameDTO [id=" + id + ", date=" + date + ", amount=" + amount + ", sellerName="
				+ name + "]";
	}
	
}
