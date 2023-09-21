package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiceRollMySQLDTO {
	
	private Integer idGame;
	private Integer idPlayer;
	private Integer firstRoll;
	private Integer secondRoll;
	private Boolean win;

}
