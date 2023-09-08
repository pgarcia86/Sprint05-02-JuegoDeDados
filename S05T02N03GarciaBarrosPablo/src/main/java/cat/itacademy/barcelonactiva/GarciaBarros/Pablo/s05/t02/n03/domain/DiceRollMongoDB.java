package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "Dice_Roll")
public class DiceRollMongoDB {
	
	@Id
	private String idGame;
	
	private Integer idPlayer;

	private Integer firstRoll;

	private Integer secondRoll;	
	
	private Boolean win;

	public DiceRollMongoDB(Integer id, Integer firstRoll, Integer secondRoll) {
		
		this.idPlayer = id;
		this.firstRoll = firstRoll;
		this.secondRoll = secondRoll;
		if(this.firstRoll + this.secondRoll == 7) {
			this.win = true;
		}
		else {
			this.win = false;
		}
		
	}	
	
	public Integer getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(Integer idPlayer) {
		this.idPlayer = idPlayer;
	}

	public Integer getFirstRoll() {
		return firstRoll;
	}

	public void setFirstRoll(Integer firstRoll) {
		this.firstRoll = firstRoll;
	}

	public Integer getSecondRoll() {
		return secondRoll;
	}

	public void setSecondRoll(Integer secondRoll) {
		this.secondRoll = secondRoll;
	}

	public String getIdGame() {
		return idGame;
	}

	public void setIdGame(String idGame) {
		this.idGame = idGame;
	}

	public Boolean getWin() {
		return win;
	}

	public void setWin(Boolean win) {
		this.win = win;
	}
}