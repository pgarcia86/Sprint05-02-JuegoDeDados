package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.LoginUser;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.UserRoles;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.AuthResponseDTO;
//import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.AuthResponseDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.LoginDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.DTO.RegisterDTO;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ILoginUser;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.IUserRoles;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.security.TokenGenerator;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private ILoginUser loginUser;
	private IUserRoles userRoles;
	private PasswordEncoder passwordEncoder;
	private TokenGenerator tokenGenerator;
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, ILoginUser iLoginUser, 
			IUserRoles iUserRoles, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator) {
		
		this.authenticationManager = authenticationManager;
		this.loginUser = iLoginUser;
		this.userRoles = iUserRoles;
		this.passwordEncoder = passwordEncoder;
		this.tokenGenerator = tokenGenerator;
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO register){
		if(loginUser.existsByUsername(register.getUsername())) {
			return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
		}
		
		LoginUser user = new LoginUser();
		user.setUsername(register.getUsername());
		user.setPassword(passwordEncoder.encode((register.getPassword())));
		
		UserRoles roles = userRoles.findByNameRol("USER").get();
		user.setRoles(Collections.singletonList(roles));
		
		loginUser.save(user);
		
		return new ResponseEntity<>("El usuario se registro correctamente", HttpStatus.OK);
	}
	
	
	@PostMapping("login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO){
		
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = tokenGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}
}
