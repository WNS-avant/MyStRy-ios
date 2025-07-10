package com.stry.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stry.entity.StorySlot;
import com.stry.service.AdminService;
import com.stry.service.StorySlotService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StorySlotService storyService;
    
    @Autowired
    private AdminService adminService;

    public AdminController(StorySlotService storyService) {
        this.storyService = storyService;
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        if (adminService.validateCredentials(username, password)) {
            return "admin";
        } else {
            model.addAttribute("error", "Either you forgot your SPELL or you are a TRESPASSER, in that case turn away you mere mortal!");
            List<StorySlot> stories = storyService.getPublicStories();
            System.out.println("Fetched public stories: " + stories.size());
            stories.forEach(s -> System.out.println(s.getTitle() + " -> " + s.isPublic()));
            model.addAttribute("stories", stories);
            return "index"; 
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadStory(
            @RequestParam("title") String title,
            @RequestParam("pdf") MultipartFile pdf,
            @RequestParam("image") MultipartFile image,
            @RequestParam("genre") String genre,
            @RequestParam("subgenres") String subgenres,
            @RequestParam("desc") String desc,
            @RequestParam(value = "isPublic", defaultValue = "false") boolean isPublic) {
        try {
            StorySlot saved = storyService.saveStory(pdf, image, title, genre, subgenres, desc, isPublic);
            return ResponseEntity.ok(saved); // Success: full StorySlot returned as JSON
        } catch (IOException e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("message", "Upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody); // JSON error
        }
    }

    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStory(@PathVariable Long id) {
        try {
            storyService.deleteStorySlot(id);
            return ResponseEntity.ok("Story deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File deletion error: " + e.getMessage());
        }
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<?> updateStorySlot(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam(value = "pdf", required = false) MultipartFile pdf,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("genre") String genre,
            @RequestParam("subgenres") String subgenres,
            @RequestParam("desc") String desc) {
        try {
            StorySlot updated = storyService.updateStorySlot(id, title, pdf, image, genre, subgenres, desc);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }

    
    @PutMapping("/toggle-visibility/{id}")
    public ResponseEntity<String> toggleVisibility(@PathVariable Long id) {
        boolean updated = storyService.toggleVisibility(id);
        return updated
            ? ResponseEntity.ok("Visibility toggled successfully.")
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found.");
    }

    @PutMapping("/increment-read/{id}")
    @ResponseBody
    public ResponseEntity<Void> incrementRead(@PathVariable Long id) {
        storyService.incrementReadCount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/increment-download/{id}")
    @ResponseBody
    public ResponseEntity<Void> incrementDownload(@PathVariable Long id) {
        storyService.incrementDownloadCount(id);
        return ResponseEntity.ok().build();
    }




}
