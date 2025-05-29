package com.github.Lidia.controller;

import lombok.RequiredArgsConstructor;
import com.github.Lidia.model.Note;
import com.github.Lidia.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    // Создать заметку
    //localhost:8080/notes
    // RequestBody:
    //{ "title" : "Заголовок 1", "contents" : "Содержимое 1" }
    //{ "title" : "Заголовок 2", "contents" : "Содержимое 2" }
    //{ "title" : "Заголовок 3", "contents" : "Содержимое 3" }
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.createNote(note), HttpStatus.CREATED);
    }

    // Вывести список всех заметок
    //localhost:8080/notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return new ResponseEntity<>(noteService.getAllNotes(), HttpStatus.OK);
    }

    // Вывести заметку по ID
    //localhost:8080/notes/1
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) {
        Note noteById;
        try {
            noteById = noteService.getNoteById(id);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Note());
        }
        return new ResponseEntity<>(noteById, HttpStatus.OK);
    }

    // Обновить заметку
    //localhost:8080/notes
    // RequestBody:
    //{ "id" : 3, "title" : "Заголовок 4", "contents" : "Содержимое 4" }
    @PutMapping
    public ResponseEntity<Note> updateNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.updateNote(note), HttpStatus.OK);
    }

    // Удалить заметку
    //localhost:8080/notes/2
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id){
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }

}
