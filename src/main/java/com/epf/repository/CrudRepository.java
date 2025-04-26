package com.epf.repository;


import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findAll();
    Optional<T> findById(Long id);
    void create(T entity);
    void update(T entity);
    void delete(Long id);
}
