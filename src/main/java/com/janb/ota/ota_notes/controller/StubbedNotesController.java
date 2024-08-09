package com.janb.ota.ota_notes.controller;

import com.janb.ota.ota_notes.api.NotesApi;
import com.janb.ota.ota_notes.model.Note;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StubbedNotesController implements NotesApi {

    @Override
    public ResponseEntity<List<Note>> getNotes() {
        return ResponseEntity.ok(List.of(new Note(1L, "Sample Title", "Sample Content")));
    }

    @Override
    public ResponseEntity<Note> createNote(Note note) {
        return ResponseEntity.ok(note);
    }

    @Override
    public ResponseEntity<Note> getNote(Long id) {
        return ResponseEntity.ok(new Note(id, "Existing Note Title", "Existing Note Content"));
    }

    @Override
    public ResponseEntity<Note> updateNote(Long id) {
        return ResponseEntity.ok(new Note(id, "Updated Note Title", "Updated Note Content"));
    }

    @Override
    public ResponseEntity<Void> deleteNote(Long id) {
        return ResponseEntity.noContent().build();
    }
}
