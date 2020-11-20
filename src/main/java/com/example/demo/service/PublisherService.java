package com.example.demo.service;

import com.example.demo.domain.Publisher;
import com.example.demo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    private boolean existsById(Long id) {
        return publisherRepository.existsById(id);
    }

    public Publisher findById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElse(null);
        if (publisher==null) {
            return null;
        }
        else return publisher;
    }

    public List<Publisher> findAll(int pageNumber, int rowPerPage) {
        List<Publisher> publisher = new ArrayList<>();
        Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage,
                Sort.by("id").ascending());
        publisherRepository.findAll(sortedByIdAsc).forEach(publisher::add);
        return publisher;
    }

    public Publisher save(Publisher publisher)  {
        if (!StringUtils.isEmpty(publisher.getName())) {
            if (publisher.getId() != null && existsById(publisher.getId())) {
                return null;
            }
            return publisherRepository.save(publisher);
        }
        else {
            return null;
        }
    }

    public void update(Publisher publisher) {
        if (!StringUtils.isEmpty(publisher.getName())) {
            if (!existsById(publisher.getId())) {
            }
            publisherRepository.save(publisher);
        }
        else {
        }
    }

    public void deleteById(Long id) {
        if (!existsById(id)) {
        }
        else {
            publisherRepository.deleteById(id);
        }
    }

    public Long count() {
        return publisherRepository.count();
    }
}
