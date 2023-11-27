package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain;

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
public class DiceRollMongoDB {
	
	private Integer idPlayer;
	
	private Integer firstRoll;

	private Integer secondRoll;	
	
	private Boolean win;

	
	public DiceRollMongoDB(Integer firstRoll, Integer secondRoll) {
	
		this.firstRoll = firstRoll;
		this.secondRoll = secondRoll;
		if(this.firstRoll + this.secondRoll == 7) {
			this.win = true;
		}
		else {
			this.win = false;
		}		
	}
	
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
	public Boolean getWin() {
		return win;
	}
	public void setWin(Boolean win) {
		this.win = win;
	}
}