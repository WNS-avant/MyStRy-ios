package com.stry.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stry.entity.StorySlot;
import com.stry.service.StorySlotService;

@RestController
@RequestMapping("/api/stories")
public class UserController {

    private final StorySlotService storyService;

    public UserController(StorySlotService storyService) {
        this.storyService = storyService;
    }
    
    
    @GetMapping
    public List<StorySlot> getAllStories() {
        return storyService.getAllStories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorySlot> getStory(@PathVariable Long id) {
        return storyService.getStoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

