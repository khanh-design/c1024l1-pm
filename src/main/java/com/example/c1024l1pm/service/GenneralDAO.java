package com.example.c1024l1pm.service;

import java.sql.SQLException;
import java.util.List;

public interface GenneralDAO <E> {
    List<E> findAll();
    List<E> findAllWithStoreProdure();
    void save(E entity) throws SQLException;
    void saveWithStoreProdure(E entity) throws SQLException;
    public E findById(int id);
    boolean update(E entity);
    E findByIdWithStoreProdure(int id);
    boolean updateWithStoreProdure(E entity);
}
