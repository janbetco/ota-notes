package com.janb.ota.ota_notes.controller;

import com.janb.ota.ota_notes.model.Note;
import com.janb.ota.ota_notes.model.NoteRequest;
import com.janb.ota.ota_notes.model.NoteResponse;
import org.mapstruct.Mapper;

@Mapper
public interface NoteMapper {

  NoteResponse toNoteResponse(Note note);

  Note toNote(NoteRequest noteRequest);

}
