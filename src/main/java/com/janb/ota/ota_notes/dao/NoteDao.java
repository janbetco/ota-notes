package com.janb.ota.ota_notes.dao;

import com.janb.ota.ota_notes.model.Note;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/**
 * A simple in-memory data storage backed by {@link java.util.concurrent.ConcurrentHashMap}
 */
@Repository
public class NoteDao implements GenericDao<Note, UUID> {

  private final Map<UUID, Note> storage = new ConcurrentHashMap<>();

  @Override
  public Iterable<Note> findAll() {
    return storage.values();
  }

  @Override
  public Optional<Note> findById(UUID id) {
    Note note = storage.get(id);
    return Optional.ofNullable(note);
  }

  @Override
  public Note save(Note note) {
    Optional<UUID> id = Optional.ofNullable(note.id());
    if (id.isEmpty()) {
      note = new Note(UUID.randomUUID(), note.title(), note.content());
    }
    storage.put(note.id(), note);
    return note;
  }

  @Override
  public void deleteById(UUID id) {
    storage.remove(id);
  }

  public void deleteAll() {
    storage.clear();
  }
}
