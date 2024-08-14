package com.janb.ota.ota_notes.model;

import java.util.UUID;

public record Note(UUID id, String title, String content) {

}
