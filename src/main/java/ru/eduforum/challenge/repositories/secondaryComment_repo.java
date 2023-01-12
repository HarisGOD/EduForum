package ru.eduforum.challenge.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.eduforum.challenge.units.secondaryComment;

public interface secondaryComment_repo extends JpaRepository<secondaryComment,Long>{

	List<secondaryComment> findAllByPrimaryCommentId(int primaryCommentId);
	
	
}