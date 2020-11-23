package com.example.demo.service;


import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    private boolean existsById(Long id) {
        return bookRepository.existsById(id);
    }

    public Book findById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book==null) {
            return null;
        }
        else return book;
    }

    public List<Book> findAll(int pageNumber, int rowPerPage, String keyword) {
        if (keyword != null){
            return bookRepository.search(keyword);
        }

        List<Book> book = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        bookRepository.findAll(sortedByIdAsc).forEach(book::add);
        return book;
    }

    public Book save(Book book)  {
        if (!StringUtils.isEmpty(book.getName())) {
            if (book.getId() != null && existsById(book.getId())) {
                return null;
            }
            return bookRepository.save(book);
        }
        else {
            return null;
        }
    }

    public void update(Book book) {
        if (!StringUtils.isEmpty(book.getName())) {
            if (!existsById(book.getId())) {
            }
            bookRepository.save(book);
        }
        else {
        }
    }

    public void deleteById(Long id) {
        if (!existsById(id)) {
        }
        else {
            bookRepository.deleteById(id);
        }
    }

    public Long count() {
        return bookRepository.count();
    }
}
