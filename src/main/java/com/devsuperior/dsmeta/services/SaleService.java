package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SalesReportDTO;
import com.devsuperior.dsmeta.dto.SalesSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
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

	public Page<SalesReportDTO> findReport(String minDate, String maxDate, String sellerName, Pageable pageable) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate;
		LocalDate endDate;
		if (!maxDate.isBlank()) {
			endDate = LocalDate.parse(maxDate, formatter);
		} else {
			endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}

		if (!minDate.isBlank()) {
			startDate = LocalDate.parse(minDate, formatter);
		} else {
			startDate = endDate.minusYears(1L);
		}

		return repository.searchReport(startDate, endDate, sellerName, pageable);
	}

	public List<SalesSummaryDTO> findSummary(String minDate, String maxDate, String sellerName) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate;
		LocalDate endDate;
		if (!maxDate.isBlank()) {
			endDate = LocalDate.parse(maxDate, formatter);
		} else {
			endDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}

		if (!minDate.isBlank()) {
			startDate = LocalDate.parse(minDate, formatter);
		} else {
			startDate = endDate.minusYears(1L);
		}
		return repository.searchSummary(startDate, endDate, sellerName);
	}


}
