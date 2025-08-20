package com.example.controller;

import com.example.dto.TutorialDto;
import com.example.service.TutorialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/tutorials")
public class TutorialController {

    private final TutorialService tutorialService;

    @GetMapping
    public ResponseEntity<List<TutorialDto>> getAllTutorials() {
        return new ResponseEntity<>(tutorialService.findAllTutorial(), HttpStatus.OK);
    }

    @GetMapping("one/{id}")
    public ResponseEntity<TutorialDto> getTutorialById(@PathVariable Integer id) {
        return new ResponseEntity<>(tutorialService.findTutorialById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<TutorialDto> addTutorial(@RequestBody TutorialDto tutorialDto) {
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


    @GetMapping("/search")
    public ResponseEntity<List<TutorialDto>> searchTutorials(@RequestParam(required = false) Boolean published, @RequestParam(required = false) String keyword) {
        List<TutorialDto> res = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty() && (published == null)) {
            res = tutorialService.findAllTutorial();
        }
        if (keyword != null && !keyword.trim().isEmpty() && (published == null)) {
            res = tutorialService.findByTitleOrDescContainingIgnoreCase(keyword);
        }
        if (keyword != null && !keyword.trim().isEmpty() && (published != null)) {
            res = tutorialService.findByPublishedAndKeyword(published, keyword);
        }
        if (keyword == null || keyword.trim().isEmpty() && (published != null)) {
            res = tutorialService.findByPublished(published);
        }


        if (res.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


}
