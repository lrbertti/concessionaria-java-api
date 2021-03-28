package io.github.lubertti.concessionariajavaapi.controller;


import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.lubertti.concessionariajavaapi.model.*;
import io.github.lubertti.concessionariajavaapi.service.*;

@RestController
@RequestMapping("/api-travels/statistics")
public class StatisticController {

	private static final Logger logger = Logger.getLogger(StatisticController.class);
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private StatisticService statisticsService;
	
	@GetMapping(produces = {"application/json"})
	public ResponseEntity<Statistic> getStatistics(){
		List<Veiculo> veiculos = veiculoService.find();
		
		Statistic statistics = statisticsService.create(veiculos);
		logger.info(statistics);
		
		return ResponseEntity.ok(statistics);
	}
}
