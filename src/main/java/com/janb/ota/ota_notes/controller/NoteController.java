package com.janb.ota.ota_notes.controller;

import com.janb.ota.ota_notes.api.NotesApi;
import com.janb.ota.ota_notes.model.Note;
import com.janb.ota.ota_notes.model.NoteRequest;
import com.janb.ota.ota_notes.model.NoteResponse;
import com.janb.ota.ota_notes.service.NoteService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController implements NotesApi {

  @Autowired
  private NoteService noteService;

  @Autowired
  private NoteMapper noteMapper;

  @Override
  public ResponseEntity<List<NoteResponse>> getNotes() {
    List<Note> notes = noteService.getNotes();
    List<NoteResponse> notesResponse = notes.stream()
        .map(noteMapper::toNoteResponse)
        .toList();
    return ResponseEntity.ok(notesResponse);
  }

  @Override
  public ResponseEntity<NoteResponse> createNote(NoteRequest noteRequest) {
    Note note = noteService.createNote(noteMapper.toNote(noteRequest));
    NoteResponse noteResponse = noteMapper.toNoteResponse(note);
    return ResponseEntity.created(URI.create("/notes/" + noteResponse.getId())).body(noteResponse);
  }

  @Override
  public ResponseEntity<NoteResponse> getNote(UUID id) {
    Optional<Note> note = noteService.getNote(id);
    if (note.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(noteMapper.toNoteResponse(note.get()));
  }

  @Override
  public ResponseEntity<NoteResponse> updateNote(UUID id, NoteRequest noteRequest) {
    boolean noteExists = noteService.getNote(id).isPresent();
    Note note = noteService.updateNote(id, noteMapper.toNote(noteRequest));
    NoteResponse noteResponse = noteMapper.toNoteResponse(note);
    if (noteExists) {
      return ResponseEntity.ok(noteResponse);
    }
    return ResponseEntity.created(URI.create("/notes/" + noteResponse.getId())).body(noteResponse);
  }

  @Override
  public ResponseEntity<Void> deleteNote(UUID id) {
    noteService.deleteNote(id);
    return ResponseEntity.noContent().build();
  }

}
