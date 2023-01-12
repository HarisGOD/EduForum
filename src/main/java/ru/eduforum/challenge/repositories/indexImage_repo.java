package ru.eduforum.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.eduforum.challenge.units.indexImage;

public interface indexImage_repo extends JpaRepository<indexImage,Long>{
	indexImage findIndexImageByID(int ID);
	indexImage findByPinTo(String PinTo);
}