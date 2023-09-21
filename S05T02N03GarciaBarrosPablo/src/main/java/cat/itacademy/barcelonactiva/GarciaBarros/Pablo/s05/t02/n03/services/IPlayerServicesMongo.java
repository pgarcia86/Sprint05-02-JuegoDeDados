package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.Date;
import java.util.List;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMongoDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMongoDTO;

public interface IPlayerServicesMongo {
	
	public List<PlayerMongoDTO> getAllPlayers();
	
	public PlayerMongoDTO addNewPlayer(Integer id, String name, String registrationDate);
	
	public DiceRollMongoDTO play(Integer id, Integer firstRoll, Integer secondRoll);
	
	public PlayerMongoDTO updatePlayerName(Integer id, String name, Date date);
	
	public void deleteDiceRolls(Integer id);
	
	public PlayerMongoDTO getOneByPlayerId(Integer id);
	
	public List<PlayerMongoDTO> getRanking();
	
	public PlayerMongoDTO getWinner();
	
	public PlayerMongoDTO getLoser();
}
