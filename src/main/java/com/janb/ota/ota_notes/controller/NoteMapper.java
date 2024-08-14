package com.janb.ota.ota_notes.controller;

import com.janb.ota.ota_notes.model.Note;
import com.janb.ota.ota_notes.model.NoteRequest;
import com.janb.ota.ota_notes.model.NoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface NoteMapper {

  NoteResponse toNoteResponse(Note note);

  @Mapping(target = "id", ignore = true)
  Note toNote(NoteRequest noteRequest);

}
