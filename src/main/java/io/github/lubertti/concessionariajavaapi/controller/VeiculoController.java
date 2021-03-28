package io.github.lubertti.concessionariajavaapi.controller;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.lubertti.concessionariajavaapi.model.Veiculo;
import io.github.lubertti.concessionariajavaapi.service.VeiculoService;

@RestController
@RequestMapping("/api-veiculos/veiculos")
public class VeiculoController {

	private static final Logger logger = Logger.getLogger(VeiculoController.class);
	
	@Autowired
	private VeiculoService veiculoService;
	
	@GetMapping
	public ResponseEntity<List<Veiculo>> find(){
		if(veiculoService.find().isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		logger.info(veiculoService.find());
		return ResponseEntity.ok(veiculoService.find());
	}
	
	@DeleteMapping
	public ResponseEntity<Boolean> delete(){
		
		try {
			veiculoService.delete();
			return ResponseEntity.noContent().build();
		}catch(Exception e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Veiculo> create(@RequestBody JSONObject veiculo){
		try {
			if(veiculoService.isJSONValid(veiculo.toString())) {
				Veiculo veiculoCreated = veiculoService.create(veiculo);
				var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(veiculoCreated.getVeiculo()).build().toUri();
				
				veiculoCreated.add(veiculoCreated);
				return ResponseEntity.created(uri).body(null);
			}
		}catch(Exception e) {
			logger.error("JSON fields are not parsable. " + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
	
	@PutMapping(path = "/{id}", produces = {"application/json"})
	public ResponseEntity<Veiculo> update(@PathVariable("id") long id, @RequestBody JSONObject veiculo){
		try {
			if(veiculoService.isJSONValid(veiculo.toString())) {
				Veiculo veiculoToUpdate = veiculoService.findById(id);
				if(veiculoToUpdate == null) {
					logger.error("Veiculo not found");
					return ResponseEntity.notFound().build();
				}else {
					Veiculo veiculoUpdated = veiculoService.update(veiculo, veiculoToUpdate);
					return ResponseEntity.ok(veiculoUpdated);
				}
			}else {
				return ResponseEntity.badRequest().body(null);
			}
		}catch(Exception e) {
			logger.error("JSON fields are not parsable" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}
}
