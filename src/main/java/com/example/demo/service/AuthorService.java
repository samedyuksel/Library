package com.example.demo.service;

import com.example.demo.domain.Author;
import com.example.demo.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    private boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    public Author findById(Long id) {
        Author author = authorRepository.findById(id).orElse(null);
        if (author==null) {
            return null;
        }
        else return author;
    }

    public List<Author> findAll(int pageNumber, int rowPerPage) {
        List<Author> author = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        authorRepository.findAll(sortedByIdAsc).forEach(author::add);
        return author;
    }

    public Author save(Author author)  {
        if (!StringUtils.isEmpty(author.getName())) {
            if (author.getId() != null && existsById(author.getId())) {
                return null;
            }
            return authorRepository.save(author);
        }
        else {
            return null;
        }
    }

    public void update(Author author) {
        if (!StringUtils.isEmpty(author.getName())) {
            if (!existsById(author.getId())) {
                }
            authorRepository.save(author);
        }
        else {
        }
    }

    public void deleteById(Long id) {
        if (!existsById(id)) {
        }
        else {
            authorRepository.deleteById(id);
        }
    }

    public Long count() {
        return authorRepository.count();
    }


}
