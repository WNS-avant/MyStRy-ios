package com.stry.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;      // ✅ Correct
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stry.entity.StorySlot;
import com.stry.repository.StorySlotRepository;



@Service
public class StorySlotServiceImpl implements StorySlotService{

	 @Value("${upload.path.pdf}")
	    private String pdfUploadPath;

	    @Value("${upload.path.image}")
	    private String imageUploadPath;

	    private final StorySlotRepository storyRepo;

	    public StorySlotServiceImpl(StorySlotRepository storyRepo) {
	        this.storyRepo = storyRepo;
	    }

	    @Override
	    public StorySlot saveStory(MultipartFile pdf, MultipartFile image, String title, String genre, String subgenres, String desc, boolean isPublic) throws IOException {
	        String pdfFileName = UUID.randomUUID() + "_" + pdf.getOriginalFilename();
	        String imageFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

	        Path pdfPath = Paths.get(pdfUploadPath, pdfFileName);
	        Path imagePath = Paths.get(imageUploadPath, imageFileName);

	        Files.copy(pdf.getInputStream(), pdfPath, StandardCopyOption.REPLACE_EXISTING);
	        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

	        StorySlot slot = new StorySlot();
	        slot.setTitle(title);
	        slot.setPdfPath("/files/pdf/" + pdfFileName);
	        slot.setImagePath("/files/images/" + imageFileName);
	        slot.setGenre(genre);
	        slot.setSubgenres(subgenres);
	        slot.setDesc(desc);
	        slot.setPublic(isPublic);
	        slot.setUploadedAt(LocalDateTime.now());

	        return storyRepo.save(slot);
	    }
	    
	    @Override
	    public void deleteStorySlot(Long id) throws IOException {
	        StorySlot slot = storyRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Story not found"));

	        // Delete physical files
	        Path pdfPath = Paths.get(slot.getPdfPath().replace("/files/pdf/", "uploads/pdfs/"));
	        Path imagePath = Paths.get(slot.getImagePath().replace("/files/images/", "uploads/images/"));

	        Files.deleteIfExists(pdfPath);
	        Files.deleteIfExists(imagePath);

	        // Delete DB entry
	        storyRepo.deleteById(id);
	    }

	    @Override
	    public StorySlot updateStorySlot(Long id, String title, MultipartFile pdf, MultipartFile image, String genre, String subgenres, String desc) throws IOException {
	        StorySlot slot = storyRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Story not found"));

	        slot.setTitle(title);
	        slot.setGenre(genre);
	        slot.setSubgenres(subgenres);
	        slot.setDesc(desc);

	        // Ensure directories exist
	        Path pdfDir = Paths.get("uploads/pdfs");
	        Path imageDir = Paths.get("uploads/images");
	        Files.createDirectories(pdfDir);
	        Files.createDirectories(imageDir);

	        if (pdf != null && !pdf.isEmpty()) {
	            String pdfFileName = UUID.randomUUID() + "_" + pdf.getOriginalFilename();
	            Path pdfPath = pdfDir.resolve(pdfFileName);
	            Files.copy(pdf.getInputStream(), pdfPath, StandardCopyOption.REPLACE_EXISTING);
	            slot.setPdfPath("/files/pdf/" + pdfFileName);
	        }

	        if (image != null && !image.isEmpty()) {
	            String imageFileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
	            Path imagePath = imageDir.resolve(imageFileName);
	            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
	            slot.setImagePath("/files/images/" + imageFileName);
	        }

	        return storyRepo.save(slot);
	    }
	    
	    public boolean toggleVisibility(Long id) {
	        Optional<StorySlot> optional = storyRepo.findById(id);
	        if (optional.isPresent()) {
	            StorySlot slot = optional.get();
	            slot.setPublic(!slot.isPublic());
	            storyRepo.save(slot);
	            return true;
	        }
	        return false;
	    }

	    @Override
	    public void incrementReadCount(Long id) {
	        storyRepo.findById(id).ifPresent(slot -> {
	            slot.setReadCount(slot.getReadCount() + 1);
	            storyRepo.save(slot);
	        });
	    }

	    @Override
	    public void incrementDownloadCount(Long id) {
	        storyRepo.findById(id).ifPresent(slot -> {
	            slot.setDownloadCount(slot.getDownloadCount() + 1);
	            storyRepo.save(slot);
	        });
	    }


	    @Override
	    public List<StorySlot> getPublicStories() {
	        return storyRepo.findByIsPublicTrue();
	    }


	    @Override
	    public Optional<StorySlot> getStoryById(Long id) {
	        return storyRepo.findById(id);
	    }

		@Override
		public List<StorySlot> getAllStories() {
			return storyRepo.findAll();
		}
	
	
}
