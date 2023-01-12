package ru.eduforum.challenge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.eduforum.challenge.units.primaryComment;

public interface primaryComment_repo extends JpaRepository<primaryComment,Long>{
	List<primaryComment> findAllByPostID(int postID);
	
}