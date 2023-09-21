package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerMySQLDTO {
	
	private Integer idPlayer;
	
	private String playerName;
	
	private Date registrationDate;
	
	private Float successRate;

}
