package com.example.demo.repository;

import com.example.demo.domain.Publisher;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher,Long>,
        JpaSpecificationExecutor<Publisher> {
}
