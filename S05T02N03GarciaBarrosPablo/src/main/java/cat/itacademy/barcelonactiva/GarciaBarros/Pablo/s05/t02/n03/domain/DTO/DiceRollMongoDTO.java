package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DiceRollMongoDTO {
	
	private Integer idPlayer;
	
	private Integer firstRoll;

	private Integer secondRoll;	
	
	private Boolean win;

}
