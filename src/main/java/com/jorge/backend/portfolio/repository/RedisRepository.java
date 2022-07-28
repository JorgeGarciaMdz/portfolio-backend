package com.jorge.backend.portfolio.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository {
    
    Map<String, Byte[]> findAll();
    Byte[] findById(String id);
    void save(Byte[] image);
    void delete(String id);
}
