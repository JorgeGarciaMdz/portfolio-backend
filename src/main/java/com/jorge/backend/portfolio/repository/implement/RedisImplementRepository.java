package com.jorge.backend.portfolio.repository.implement;

import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import com.jorge.backend.portfolio.repository.RedisRepository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisImplementRepository implements RedisRepository{

    private static final String KEY = "String";

    private RedisTemplate<String, Byte[]> redisTemplate;
    private HashOperations hashOperations;

    public RedisImplementRepository(RedisTemplate<String, Byte[]> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, Byte[]> findAll() {
        return this.hashOperations.entries(KEY);
    }

    @Override
    public Byte[] findById(String id) {
        return (Byte[]) hashOperations.get(KEY, id);
    }

    @Override
    public void save(Byte[] image) {
        hashOperations.put(KEY, UUID.randomUUID().toString(), image);       
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);        
    }
    
}
