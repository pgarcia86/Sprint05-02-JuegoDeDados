package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "player")
public class PlayerMySQL {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_player")
	private Integer idPlayer;
	
	@Column(name = "player_name", unique = true)
	private String playerName;
	
	@Column(name = "registration_date")
	private Date registrationDate;
	
	@Column(name = "success_rate")
	private Float successRate;
	
	public PlayerMySQL(String name, String date) {
		
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


	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Float getSuccessRate() {
		return this.successRate;
	}
	
	public void setSuccessRate(Float successRate) {
		this.successRate = successRate;
	}
	
	

}
