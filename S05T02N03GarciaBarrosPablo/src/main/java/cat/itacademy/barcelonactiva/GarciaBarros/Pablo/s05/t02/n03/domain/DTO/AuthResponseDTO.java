package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO;

import lombok.Data;

@Data
public class AuthResponseDTO {

	private String accessToken;
	private String tokenType = "Bearer ";
	
	
	public AuthResponseDTO(String accessToken) {
		this.accessToken = accessToken;
	}
}
