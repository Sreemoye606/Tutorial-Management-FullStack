package com.example.mapper;

import com.example.dto.TutorialDto;
import com.example.entity.Tutorial;

public class TutorialMapper {

    public static TutorialDto createDto(Tutorial tutorial) {
        return new TutorialDto(
                tutorial.getId(),
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.getPublished()
        );
    }

    public static Tutorial createOriginal(TutorialDto tutorialDto) {
        return new Tutorial(
                tutorialDto.getId(),
                tutorialDto.getTitle(),
                tutorialDto.getDescription(),
                tutorialDto.getPublished()
        );
    }
}
