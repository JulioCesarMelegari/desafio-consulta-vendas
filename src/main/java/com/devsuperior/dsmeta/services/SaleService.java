package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleWithSellerNameDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleWithSellerNameProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleWithSellerNameDTO> search(String name, String minDateString, String maxDateString, Pageable pageable){
		
		LocalDate maxDate = returnMaxDate(maxDateString);
		LocalDate minDate = returnMinDate(minDateString);
		
		 Page<SaleWithSellerNameProjection> list;

		    if (name != null && !name.trim().isEmpty()) {
		        name = "%" + name.trim() + "%";
		        list = repository.search1(name, minDate, maxDate, pageable);
		    } else {
		        list = repository.search2(minDate, maxDate, pageable);
		    }

		    Page<SaleWithSellerNameDTO> result = list.map(SaleWithSellerNameDTO::new);
		    return result;
	}
	
	protected LocalDate returnMaxDate (String date) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maxDate;
		maxDate = (date != null) ? LocalDate.parse(date) : today;
		return maxDate;
	}
	
	protected LocalDate returnMinDate (String date) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate minDate;
		minDate = (date != null) ? LocalDate.parse(date) : today.minusYears(1);
		return minDate;
	}
}
