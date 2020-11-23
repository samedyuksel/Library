package com.example.demo.repository;

import com.example.demo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book,Long>,
        JpaSpecificationExecutor<Book>, JpaRepository<Book, Long> {

    @Query( "SELECT b FROM Book b WHERE CONCAT(b.name,' ',b.ISBN,' ') LIKE %?1%" )
    List<Book> search(String keyword);
}
