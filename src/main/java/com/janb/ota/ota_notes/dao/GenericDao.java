package com.janb.ota.ota_notes.dao;

import java.util.Optional;

public interface GenericDao<T, ID> {

  Iterable<T> findAll();

  Optional<T> findById(ID id);

  T save(T entity);

  void deleteById(ID id);

}
