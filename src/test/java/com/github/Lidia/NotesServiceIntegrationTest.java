package com.github.Lidia;

import com.github.Lidia.model.Note;
import com.github.Lidia.repository.NoteRepository;
import com.github.Lidia.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Интеграционные тесты
@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext
public class NotesServiceIntegrationTests {

    // Создание тестируемого репозитория
    @Autowired
    private NoteRepository noteRepository;

    // Создание тестируемого сервиса
    @Autowired
    private NoteServiceImpl noteServiceImpl;

    // Создание заметки для использования в тестах
    private Note note;

    // Инициализация заметки перед каждым тестом
    @BeforeEach
    public void setup() {
        note = new Note();
        note.setTitle("Тестовая заметка");
        note.setContents("Содержимое тестовой заметки");
        note.setCreationDate(LocalDateTime.now());
    }

    // Тестирование создания заметки
    @Test
    public void createNoteTest() {

        Note createdNote = noteServiceImpl.createNote(note);

        assertNotNull(createdNote.getId());
        assertEquals(note.getTitle(), createdNote.getTitle());
        assertEquals(note.getContents(), createdNote.getContents());

    }

    // Тестирование получения всех заметок
    @Test
    public void getAllNotesTest() {

        noteServiceImpl.createNote(note);

        List<Note> allNotes = noteServiceImpl.getAllNotes();

        assertEquals(1, allNotes.size());
        assertEquals(note, allNotes.get(0));

    }

    // Тестирование получения заметки по ID
    @Test
    public void getNoteByIdTest() {

        Note createdNote = noteServiceImpl.createNote(note);

        Note fetchedNote = noteServiceImpl.getNoteById(createdNote.getId());

        assertEquals(createdNote, fetchedNote);

    }

    // Тестирование обновления заметки
    @Test
    public void updateNoteTest() {

        Note createdNote = noteServiceImpl.createNote(note);

        Note updatedNote = new Note();
        updatedNote.setId(createdNote.getId());
        updatedNote.setTitle("Обновленная тестовая заметка");
        updatedNote.setContents("Содержимое обновленной тестовой заметки");

        Note updatedFetchedNote = noteServiceImpl.updateNote(updatedNote);

        assertEquals(updatedNote.getId(), updatedFetchedNote.getId());
        assertEquals(updatedNote.getTitle(), updatedFetchedNote.getTitle());
        assertEquals(updatedNote.getContents(), updatedFetchedNote.getContents());

    }

    // Тестирование удаления заметки
    @Test
    public void deleteNoteTest() {

        Note createdNote = noteServiceImpl.createNote(note);

        noteServiceImpl.deleteNote(createdNote.getId());

        assertNull(noteRepository.findById(createdNote.getId()).orElse(null));
    }

}
