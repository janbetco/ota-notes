package com.janb.ota.ota_notes;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.janb.ota.ota_notes.dao.NoteDao;
import com.janb.ota.ota_notes.model.Note;
import com.janb.ota.ota_notes.model.NoteRequest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class NotesIT {

  public static final UUID TEST_DATA_UUID = UUID.fromString("5d0411aa-280b-4150-835a-dc5c84f6e4f8");

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private NoteDao noteDao;

  @BeforeEach
  public void setUp() {
    noteDao.deleteAll();
  }

  @Test
  public void shouldCreateNote() throws Exception {
    mockMvc.perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteRequest())
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").value("New Title"))
        .andExpect(jsonPath("$.content").value("New Content"));
  }

  @Test
  public void shouldNotCreateNoteWithMissingFields() throws Exception {
    mockMvc.perform(
            post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.errors").exists());
  }

  @Test
  public void shouldRetrieveAllNotes() throws Exception {
    addTestDataToDatastore();

    mockMvc.perform(
            get("/notes")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].title").value("Existing Title"))
        .andExpect(jsonPath("$[0].content").value("Existing Content"));
  }

  @Test
  public void shouldRetrieveEmptyNotes() throws Exception {
    mockMvc.perform(
            get("/notes")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string("[]"));
  }

  @Test
  public void shouldRetrieveNote() throws Exception {
    addTestDataToDatastore();

    mockMvc.perform(
            get("/notes/" + TEST_DATA_UUID)
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").value("Existing Title"))
        .andExpect(jsonPath("$.content").value("Existing Content"));
  }

  @Test
  public void shouldErrorWhenNoteNotFound() throws Exception {
    addTestDataToDatastore();

    mockMvc.perform(
            get("/notes/" + UUID.randomUUID())
        )
        .andExpect(status().isNotFound())
        .andReturn();
  }

  @Test
  public void shouldUpdateNote() throws Exception {
    addTestDataToDatastore();

    mockMvc.perform(
            put("/notes/" + TEST_DATA_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteRequest())
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").value("New Title"))
        .andExpect(jsonPath("$.content").value("New Content"));
  }

  @Test
  public void shouldCreateWhenTryingToUpdateNonExistentNote() throws Exception {
    mockMvc.perform(
            put("/notes/" + TEST_DATA_UUID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noteRequest())
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.title").value("New Title"))
        .andExpect(jsonPath("$.content").value("New Content"));
  }

  @Test
  public void shouldDeleteNote() throws Exception {
    addTestDataToDatastore();

    mockMvc.perform(
            delete("/notes/" + TEST_DATA_UUID)
        )
        .andExpect(status().isNoContent());
  }

  private String noteRequest() throws JsonProcessingException {
    NoteRequest noteRequest = new NoteRequest("New Title", "New Content");
    return objectMapper.writeValueAsString(noteRequest);
  }

  private void addTestDataToDatastore() {
    noteDao.save(new Note(TEST_DATA_UUID, "Existing Title", "Existing Content"));
  }
}
