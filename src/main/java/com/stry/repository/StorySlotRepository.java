package com.stry.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stry.entity.StorySlot;

@Repository
public interface StorySlotRepository extends JpaRepository<StorySlot, Long> {
	List<StorySlot> findByIsPublicTrue();

}

