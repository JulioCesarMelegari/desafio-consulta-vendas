package com.devsuperior.dsmeta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.services.SaleService;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleWithSellerNameDTO>> getReport(@RequestParam(required = false) String name, @RequestParam(required = false) String minDate, 
			@RequestParam(required = false) String maxDate, Pageable pageable) {
	    Page<SaleWithSellerNameDTO> list = service.search(name, minDate, maxDate, pageable);
	    return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<Page<SummaryDTO>> getSummary(@RequestParam(required = false) String minDate, @RequestParam(required = false) String maxDate, Pageable pageable) {
		Page<SummaryDTO> list = service.sumary(minDate, maxDate, pageable);
	    return ResponseEntity.ok(list);
	}
}
