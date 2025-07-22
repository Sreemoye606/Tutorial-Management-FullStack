package com.example.service;

import com.example.dto.TutorialDto;
import com.example.entity.Tutorial;

import java.util.List;

public interface TutorialService {
    List<TutorialDto> findAllTutorial();
    TutorialDto findTutorialById(int id);
    TutorialDto saveTutorial(TutorialDto tutorialDto);
    TutorialDto updateTutorial(int id, TutorialDto tutorialDto);
    TutorialDto deleteTutorial(int id);

}
