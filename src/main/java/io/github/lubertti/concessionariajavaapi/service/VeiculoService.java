package io.github.lubertti.concessionariajavaapi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.lubertti.concessionariajavaapi.model.Veiculo;
import net.sf.json.JSONObject;

@Service
public class VeiculoService {
	
	private List<Veiculo> veiculos;
	
	public void createVeiculoList() {
		if(veiculos == null) {
			veiculos = new ArrayList<>();
		}
	}
	
	public boolean isJSONValid(String jsonInString) {
	    try {
	       return new ObjectMapper().readTree(jsonInString) != null;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	/*
	 * Method parse do campo Id
	 */
	private long parseId(JSONObject veiculo) {
		return Long.valueOf((int) veiculo.get("id"));
	}
	
	/*
	 * Method parse do campo created
	 */
	private LocalDateTime parseCreatedDate(JSONObject veiculo) {
		
		var created = (String) veiculo.get("created");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(created, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
		
	}
	
	private LocalDateTime parseUpdateDate(JSONObject veiculo) {
		
		var updated = (String) veiculo.get("updated");
		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		return ZonedDateTime.parse(updated, formatter.withZone(ZoneId.of("UTC"))).toLocalDateTime();
	}
	
	private void setVeiculoValues(JSONObject jsonVeiculo, Veiculo veiculo) {
		
		String veiculos = (String) jsonVeiculo.get("veiculo");
		String marca = (String) jsonVeiculo.get("marca");
		int ano = (int) jsonVeiculo.get("ano");
		String descricao = (String) jsonVeiculo.get("descricao");
		boolean vendido = (boolean) jsonVeiculo.get("vendido");
		
		veiculo.setVeiculo(veiculos != null ? veiculos: veiculo.getVeiculo());
		veiculo.setMarca(marca != null ? marca : veiculo.getMarca());
		veiculo.setAno(ano != 0 ? ano : veiculo.getAno());
		veiculo.setDescricao(descricao != null ? descricao : veiculo.getDescricao());
		veiculo.setVendido(vendido ? vendido : veiculo.getVendido());
		veiculo.setCreated(jsonVeiculo.get("created") != null ? parseCreatedDate(jsonVeiculo) : veiculo.getCreated());
		veiculo.setUpdated(jsonVeiculo.get("updated") != null ? parseUpdateDate(jsonVeiculo) : veiculo.getUpdated() );
	}
	
	/*
	 * Metodo para criar um veiculo
	 */
	public Veiculo create(JSONObject jsonVeiculo) {
		
		Veiculo veiculo;
		veiculo.setId(parseId(jsonVeiculo));
		setVeiculoValues(jsonVeiculo, veiculo);
		
		return veiculo;
	}
	
	public Veiculo update(JSONObject jsonVeiculo, Veiculo veiculo) {
		setVeiculoValues(jsonVeiculo, veiculo);
		return veiculo;
	}
	
	public void add(Veiculo veiculo) {
		createVeiculoList();
		veiculos.add(veiculo);
	}
	
	public List<Veiculo> find(){
		createVeiculoList();
		return veiculos;
	}
	
	public Veiculo findById(long id) {
		return veiculos.stream().filter(v -> id == v.getId()).collect(Collectors.toList()).get(0);
	}
	
	public void delete() {
		veiculos.clear();
	}
	
	public void clearObjects() {
		veiculos = null;
	}
}
