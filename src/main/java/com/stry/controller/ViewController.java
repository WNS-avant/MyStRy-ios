package com.stry.controller;


import com.stry.entity.StorySlot;
import com.stry.service.StorySlotService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final StorySlotService storyService;

    public ViewController(StorySlotService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/")
    public String showUserDashboard(Model model) {
        List<StorySlot> stories = storyService.getPublicStories();
        System.out.println("Fetched public stories: " + stories.size());
        stories.forEach(s -> System.out.println(s.getTitle() + " -> " + s.isPublic()));
        model.addAttribute("stories", stories);
        return "index";
    }
}
