package io.github.lubertti.concessionariajavaapi.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

		private Long id;
		private String veiculo;
		private String marca;
		private int ano;
		private String descricao;
		private boolean vendido;
		private LocalDateTime created;
		private LocalDateTime updated;

}
