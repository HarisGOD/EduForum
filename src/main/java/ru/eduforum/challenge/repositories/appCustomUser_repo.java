package ru.eduforum.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.eduforum.challenge.units.appCustomUser;

public interface appCustomUser_repo extends JpaRepository<appCustomUser,Long>{
	appCustomUser findByUsername(String username);
	appCustomUser findByLogin(String login);
}
	
