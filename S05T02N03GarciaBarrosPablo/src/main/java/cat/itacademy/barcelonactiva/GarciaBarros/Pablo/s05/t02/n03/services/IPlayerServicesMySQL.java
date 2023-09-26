package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.services;

import java.util.List;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.DiceRollMySQLDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.PlayerMySQLDTO;


public interface IPlayerServicesMySQL {
	
	public List<PlayerMySQLDTO> getAllPlayers();
	
	public PlayerMySQLDTO addNewPlayer(String name, String date);
	
	public PlayerMySQLDTO updatePlayerName(Integer id, String name);	
	
	public List<PlayerMySQLDTO> getRanking();

	public PlayerMySQLDTO getLoser();

	public PlayerMySQLDTO getWinner();
	
	public DiceRollMySQLDTO play(Integer id);
	
	public PlayerMySQLDTO getOneByPlayerId(Integer id);
	
	public void deleteDiceRolls (Integer id);


}
