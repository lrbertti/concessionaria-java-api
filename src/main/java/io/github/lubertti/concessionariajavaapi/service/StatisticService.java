package io.github.lubertti.concessionariajavaapi.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.lubertti.concessionariajavaapi.model.Statistic;
import io.github.lubertti.concessionariajavaapi.model.Veiculo;

public class StatisticService {

	public Statistic create(List<Veiculo> veiculos) {
		var statistics = new Statistic();
		
		statistics.setCount(veiculos.stream().count());
		statistics.setAvg(BigDecimal.valueOf(veiculos.stream().mapToDouble(v -> v.get)));
	}
}
