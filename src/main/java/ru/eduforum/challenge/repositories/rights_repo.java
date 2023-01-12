package ru.eduforum.challenge.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.eduforum.challenge.units.rights;

public interface rights_repo extends JpaRepository<rights,Long>{
	Collection<rights> findAllByLogin(String Login);
	
	
}