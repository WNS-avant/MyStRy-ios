package com.stry.controller;


import com.stry.entity.StorySlot;
import com.stry.service.StorySlotService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Optional: Prevent caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Redirect to index page (root or wherever your landing page is)
        return "redirect:/";
    }
    
    

}
