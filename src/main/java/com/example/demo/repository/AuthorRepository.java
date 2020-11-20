package com.example.demo.repository;

import com.example.demo.domain.Author;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorRepository extends PagingAndSortingRepository<Author,Long>,
        JpaSpecificationExecutor<Author> {
}
