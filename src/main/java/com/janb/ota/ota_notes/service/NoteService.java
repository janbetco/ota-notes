package com.janb.ota.ota_notes.service;

import com.janb.ota.ota_notes.dao.NoteDao;
import com.janb.ota.ota_notes.model.Note;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteDao noteDao;

  public List<Note> getNotes() {
    return StreamSupport.stream(noteDao.findAll().spliterator(), false)
        .toList();
  }

  public Note createNote(Note note) {
    return noteDao.save(new Note(null, note.title(), note.content()));
  }

  public Note updateNote(UUID id, Note note) {
    return noteDao.save(new Note(id, note.title(), note.content()));
  }

  public Optional<Note> getNote(UUID id) {
    return noteDao.findById(id);
  }

  public void deleteNote(UUID id) {
    noteDao.deleteById(id);
  }
}
