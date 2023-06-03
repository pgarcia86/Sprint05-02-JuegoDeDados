 package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.LoginUser;

public interface ILoginUser extends JpaRepository<LoginUser, Integer>{
	
	Optional<LoginUser> findByUsername(String username);
	Boolean existsByUsername(String username);
}
