package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.domain.UserRoles;

public interface IUserRoles extends JpaRepository<UserRoles, Integer>{
	
	Optional<UserRoles> findByNameRol(String name);
	
}
