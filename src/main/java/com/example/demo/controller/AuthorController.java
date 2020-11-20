package com.example.demo.controller;

import com.example.demo.domain.Author;
import com.example.demo.service.AuthorService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private AuthorService authorService;


    @GetMapping(value = "/authors")
    public String getAuthors(Model model,
                              @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Author> authors = authorService.findAll(pageNumber, ROW_PER_PAGE);

        long count = authorService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("authors", authors);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "authorViews/author-list";
    }

    @GetMapping(value="/authors/{authorId}")
    public String getAuthorById(Model model, @PathVariable long authorId){
        Author author = null;
        try{
            author=authorService.findById(authorId);
        }catch (Exception ex){
            model.addAttribute("errorMessage","Author not found");
        }
        model.addAttribute("author",author);
        return "authorViews/author";
    }

    @GetMapping(value = {"/authors/add"})
    public String showAddAuthor(Model model) {
        Author author = new Author();
        model.addAttribute("add", true);
        model.addAttribute("author", author);

        return "authorViews/author-edit";
    }

    @PostMapping(value = "/authors/add")
    public String addAuthor(Model model,
                             @ModelAttribute("author") Author author) {
        try {
            Author newAuthor = authorService.save(author);
            return "redirect:/authors/" + String.valueOf(newAuthor.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "authorViews/author-edit";
        }
    }

    @GetMapping(value = {"/authors/{authorId}/edit"})
    public String showEditAuthor(Model model, @PathVariable long authorId) {
        Author author = new Author();
        try {
            author = authorService.findById(authorId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Contact not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("author", author);
        return "authorViews/author-edit";
    }

    @PostMapping(value = {"/authors/{authorId}/edit"})
    public String updateAuthor(Model model,
                                @PathVariable long authorId,
                                @ModelAttribute("author") Author author) {
        try {
            author.setId(authorId);
            authorService.update(author);
            return "redirect:/authors/" + String.valueOf(author.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "authorViews/author-edit";
        }
    }

    @GetMapping(value = {"/authors/{authorId}/delete"})
    public String showDeleteAuthorById(
            Model model, @PathVariable long authorId) {
        Author author = null;
        try {
            author = authorService.findById(authorId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Author not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("author", author);
        return "authorViews/author";
    }

    @PostMapping(value = {"/authors/{authorId}/delete"})
    public String deleteAuthorById(
            Model model, @PathVariable long authorId) {
        try {
            authorService.deleteById(authorId);
            return "redirect:/authors";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "authorViews/author";
        }
    }

}
