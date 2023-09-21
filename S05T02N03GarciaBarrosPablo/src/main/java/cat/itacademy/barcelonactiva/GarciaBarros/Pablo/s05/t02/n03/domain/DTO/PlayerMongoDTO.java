package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO;

import java.util.Date;
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
public class PlayerMongoDTO {
	
	private Integer idPlayer;
	
	private String playerName;

	private Date registrationDate;
	
	private Float successRate;

}
