package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int ROW_PER_PAGE = 5;

    @Autowired
    private BookService bookService;


    @GetMapping(value = "/books")
    public String getBooks(Model model,
                             @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
        List<Book> books = bookService.findAll(pageNumber, ROW_PER_PAGE);
        long count = bookService.count();
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
        model.addAttribute("books", books);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", pageNumber - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", pageNumber + 1);
        return "bookViews/book-list";
    }

    @GetMapping(value="/books/{bookId}")
    public String getBookById(Model model, @PathVariable long bookId){
        Book book = null;
        try{
            book=bookService.findById(bookId);
        }catch (Exception ex){
            model.addAttribute("errorMessage","Book not found");
        }
        model.addAttribute("book",book);
        return "bookViews/book";
    }

    @GetMapping(value = {"/books/add"})
    public String showAddBook(Model model) {
        Book book = new Book();
        model.addAttribute("add", true);
        model.addAttribute("book", book);

        return "bookViews/book-edit";
    }

    @PostMapping(value = "/books/add")
    public String addBook(Model model,
                            @ModelAttribute("book") Book book) {
        try {
            Book newBook = bookService.save(book);
            return "redirect:/books/" + String.valueOf(newBook.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            return "bookViews/book-edit";
        }
    }

    @GetMapping(value = {"/books/{bookId}/edit"})
    public String showEditBook(Model model, @PathVariable long bookId) {
        Book book = new Book();
        try {
            book = bookService.findById(bookId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Book not found");
        }
        model.addAttribute("add", false);
        model.addAttribute("book", book);
        return "bookViews/book-edit";
    }

    @PostMapping(value = {"/books/{bookId}/edit"})
    public String updateBook(Model model,
                               @PathVariable long bookId,
                               @ModelAttribute("book") Book book) {
        try {
            book.setId(bookId);
            bookService.update(book);
            return "redirect:/books/" + String.valueOf(book.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", false);
            return "bookViews/book-edit";
        }
    }

    @GetMapping(value = {"/books/{bookId}/delete"})
    public String showDeleteBookById(
            Model model, @PathVariable long bookId) {
        Book book = null;
        try {
            book = bookService.findById(bookId);
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Book not found");
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("book", book);
        return "bookViews/book";
    }

    @PostMapping(value = {"/books/{bookId}/delete"})
    public String deleteBookById(
            Model model, @PathVariable long bookId) {
        try {
            bookService.deleteById(bookId);
            return "redirect:/books";
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "bookViews/book";
        }
    }
}
