package com.stry.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.stry.entity.StorySlot;

public interface StorySlotService {
    StorySlot saveStory(MultipartFile pdf, MultipartFile image, String title,String genre, String subgeres, String desc, boolean isPublic) throws IOException;
    List<StorySlot> getPublicStories();
    Optional<StorySlot> getStoryById(Long id);
    void deleteStorySlot(Long id) throws IOException;
    StorySlot updateStorySlot(Long id, String title, MultipartFile pdf, MultipartFile image, String genre, String subgenres, String desc) throws IOException;
	List<StorySlot> getAllStories();
	boolean toggleVisibility(Long id);
	void incrementReadCount(Long id);
	void incrementDownloadCount(Long id);
	void resetReadCount(Long id);
	void resetDownloadCount(Long id);

}

