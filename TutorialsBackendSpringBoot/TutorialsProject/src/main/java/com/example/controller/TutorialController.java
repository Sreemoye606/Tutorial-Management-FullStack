package com.example.controller;

import com.example.dto.TutorialDto;
import com.example.entity.Tutorial;
import com.example.service.TutorialService;
import com.example.service.TutorialServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping
public class TutorialController {

    private final TutorialService tutorialService;
    private final TutorialServiceImpl tutorialServiceImpl;

    @GetMapping
    public ResponseEntity<List<TutorialDto>> getAllTutorials() {
        return new ResponseEntity<>(tutorialService.findAllTutorial(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorialDto> getTutorialById(@PathVariable Integer id) {
        return new ResponseEntity<>(tutorialService.findTutorialById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<TutorialDto> addTutorial(@RequestBody TutorialDto tutorialDto) {
//        tutorialService.saveTutorial(tutorialDto);
        return new ResponseEntity<>(tutorialService.saveTutorial(tutorialDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<TutorialDto> updateTutorial(@PathVariable int id ,@RequestBody TutorialDto tutorialDto) {
        return new ResponseEntity<>(tutorialService.updateTutorial(id,tutorialDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<TutorialDto> deleteTutorial(@PathVariable int id) {
        return new ResponseEntity<>(tutorialService.deleteTutorial(id), HttpStatus.OK);
    }

    @GetMapping("/{title}")
    public ResponseEntity<TutorialDto> getTutorialByTitle(@PathVariable String title) {
//        return new ResponseEntity<>(tutorialService.findTutorialByTitle(title), HttpStatus.OK);
        return null;
    }

//    @GetMapping("/search/{word}")
//    public ResponseEntity<List<TutorialDto>> searchTutorialContaining(@PathVariable String word) {
//        return new ResponseEntity<>(tutorialServiceImpl.findByTitleContainingIgnoreCase(word), HttpStatus.OK);
//    }

//    @GetMapping("/search/{word}")
//    public ResponseEntity<List<TutorialDto>> searchTutorialContaining(@PathVariable String word) {
//        return new ResponseEntity<>(tutorialServiceImpl.findByTitleOrDescContainingIgnoreCase(word, word), HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<TutorialDto>> searchTutorials(@RequestParam(required = false) Boolean published, @RequestParam(required = false) String keyword) {
        List<TutorialDto> res = new ArrayList<>();

        if (keyword == null || keyword.isEmpty() && (published == null)) {
            res = tutorialService.findAllTutorial();
        }
        if (keyword != null && !keyword.isEmpty() && (published == null)) {
            res = tutorialServiceImpl.findByTitleOrDescContainingIgnoreCase(keyword, keyword);
        }
        if (keyword != null && !keyword.isEmpty() && (published != null)) {
            res = tutorialServiceImpl.findByPublishedAndKeyword(published, keyword);
        }
        if (keyword == null || keyword.isEmpty() && (published != null)) {
            res = tutorialServiceImpl.findByPublished(published);
        }


        if (res.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


}
