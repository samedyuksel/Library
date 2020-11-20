package com.example.demo.controller;



import com.example.demo.domain.Publisher;
import com.example.demo.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PublisherController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private PublisherService publisherService;



    @GetMapping(value = "/publishers")
    public String getPublishers(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Publisher> publishers = publisherService.findAll(pageNumber, ROW_PER_PAGE);
        long count = publisherService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("publishers", publishers);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "publisherViews/publisher-list";
    }

    @GetMapping(value="/publishers/{publisherId}")
    public String getPublisherById(Model model, @PathVariable long publisherId){
        Publisher publisher = null;
        try{
            publisher=publisherService.findById(publisherId);
        }catch (Exception ex){
            model.addAttribute("errorMessage","Publisher not found");
        }
        model.addAttribute("publisher",publisher);
        return "publisherViews/publisher";
    }

    @GetMapping(value = {"/publishers/add"})
    public String showAddPublisher(Model model) {
        Publisher publisher = new Publisher();
        model.addAttribute("add", true);
        model.addAttribute("publisher", publisher);

        return "publisherViews/publisher-edit";
    }

    @PostMapping(value = "/publishers/add")
    public String addPublisher(Model model,
                          @ModelAttribute("publisher") Publisher publisher) {
        try {
            Publisher newPublisher = publisherService.save(publisher);
            return "redirect:/publishers/" + String.valueOf(newPublisher.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "publisherViews/publisher-edit";
        }
    }

    @GetMapping(value = {"/publishers/{publisherId}/edit"})
    public String showEditPublisher(Model model, @PathVariable long publisherId) {
        Publisher publisher = new Publisher();
        try {
            publisher = publisherService.findById(publisherId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Publisher not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("publisher", publisher);
        return "publisherViews/publisher-edit";
    }

    @PostMapping(value = {"/publishers/{publisherId}/edit"})
    public String updatePublisher(Model model,
                             @PathVariable long publisherId,
                             @ModelAttribute("publisher") Publisher publisher) {
        try {
            publisher.setId(publisherId);
            publisherService.update(publisher);
            return "redirect:/publishers/" + String.valueOf(publisher.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "publisherViews/publisher-edit";
        }
    }

    @GetMapping(value = {"/publishers/{publisherId}/delete"})
    public String showDeletePublisherById(
            Model model, @PathVariable long publisherId) {
        Publisher publisher = null;
        try {
            publisher = publisherService.findById(publisherId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Publisher not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("publisher", publisher);
        return "publisherViews/publisher";
    }

    @PostMapping(value = {"/publishers/{publisherId}/delete"})
    public String deletePublisherById(
            Model model, @PathVariable long publisherId) {
        try {
            publisherService.deleteById(publisherId);
            return "redirect:/publishers";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "publisherViews/publisher";
        }
    }
}
