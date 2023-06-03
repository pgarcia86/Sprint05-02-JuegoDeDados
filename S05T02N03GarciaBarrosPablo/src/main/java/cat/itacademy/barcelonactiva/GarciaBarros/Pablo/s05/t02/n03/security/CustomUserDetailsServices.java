package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.LoginUser;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.UserRoles;
import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository.ILoginUser;

@Service
public class CustomUserDetailsServices implements UserDetailsService{

	private ILoginUser loginUser;
	
	@Autowired
	public CustomUserDetailsServices(ILoginUser loginUser) {
		this.loginUser = loginUser;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		LoginUser user  = loginUser.findByUsername(email)
				.orElseThrow(()-> new UsernameNotFoundException("El usuario no esta cargado"));
			
		return new User(user.getUsername(), user.getPassword(), mapRoles(user.getRoles()));
	}

	private Collection<GrantedAuthority> mapRoles(List<UserRoles> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNameRol())).collect(Collectors.toList());
	}
}
