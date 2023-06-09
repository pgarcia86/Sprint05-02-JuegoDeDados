package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
@Document(collection = "Player")
public class PlayerMongoDB {

	
	@Id
	private Integer idPlayer;
	
	private String playerName;

	private Date registrationDate;
	
	//Con esta notacion hago referencia a un documento en el que esta la lista de DiceRoll
	@DBRef
	private List<DiceRollMongoDB> diceRolls;
	
	public PlayerMongoDB(Integer id, String name, String date) {
		
		this.idPlayer = id;
		
		this.playerName = name;
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			java.util.Date parsedDate = dateFormat.parse(date);
			this.registrationDate = new java.sql.Date(parsedDate.getTime());
		}
		catch (ParseException e){
			e.printStackTrace();
		}
	}


	public PlayerMongoDB(Integer id, String name, Date date) {
		this.idPlayer = id;
		this.playerName = name;
		this.registrationDate = date;
	}

	public Integer getIdPlayer() {
		return idPlayer;
	}

	public void setIdPlayer(Integer idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date date) {
		this.registrationDate = date;
	}

	public List<DiceRollMongoDB> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<DiceRollMongoDB> diceRolls) {
		this.diceRolls = diceRolls;
	}
	
}
