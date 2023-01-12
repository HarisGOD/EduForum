package ru.eduforum.challenge.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.eduforum.challenge.units.post;

public interface post_repo extends JpaRepository<post,Long>{
	post getPostByName(String name);
	List<post> getAllByCreated(Date Created);
	post getPostByID(int ID);
	List<post> getAllByCreator(String Creator);
}