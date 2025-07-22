package com.example.service;

import com.example.dto.TutorialDto;
import com.example.entity.Tutorial;
import com.example.mapper.TutorialMapper;
import com.example.repository.TutorialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TutorialServiceImpl implements TutorialService {

    private TutorialRepository tutorialRepository;

    @Override
    public List<TutorialDto> findAllTutorial() {

        List<Tutorial> tut = tutorialRepository.findAll();
        List<TutorialDto> tutDto = tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
        return tutDto;
    }

    @Override
    public TutorialDto findTutorialById(int id) {
        Tutorial org = tutorialRepository.findById(id).get();
        return TutorialMapper.createDto(org);
    }

    @Override
    public TutorialDto saveTutorial(TutorialDto tutorialDto) {
        Tutorial org = TutorialMapper.createOriginal(tutorialDto);
        Tutorial saved = tutorialRepository.save(org);
        return TutorialMapper.createDto(saved);
    }

    @Override
    public TutorialDto updateTutorial(int id, TutorialDto tutorialDto) {
        Tutorial org = tutorialRepository.findById(id).get();
        org.setTitle(tutorialDto.getTitle());
        org.setDescription(tutorialDto.getDescription());
        org.setPublished(tutorialDto.getPublished());
        tutorialRepository.save(org);
        return TutorialMapper.createDto(org);
    }

    @Override
    public TutorialDto deleteTutorial(int id) {
        Tutorial org = tutorialRepository.findById(id).get();
        tutorialRepository.deleteById(id);
        return TutorialMapper.createDto(org);
    }

// query methods
    public TutorialDto findTutorialByTitle(String title) {

        Tutorial org = tutorialRepository.findTutorialByTitle(title);
        return TutorialMapper.createDto(org);
    }

    public List<TutorialDto> findByTitleContainingIgnoreCase(String word) {
        List<Tutorial> tut = tutorialRepository.findByTitleContainingIgnoreCase(word);
        List<TutorialDto> tutDto = tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
        return tutDto;
    }

    public List<TutorialDto> findByPublished(Boolean published) {
        List<Tutorial> tut = tutorialRepository.findByPublished(published);
        List<TutorialDto> tutDto= tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
        return tutDto;
    }

    public List<TutorialDto> findByTitleOrDescContainingIgnoreCase(String word1, String word2) {
        List<Tutorial> tut = tutorialRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(word1, word2);
        List<TutorialDto> tutDto = tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
        return tutDto;
    }

    public List<TutorialDto> findByPublishedAndKeyword(Boolean published,String word) {
        List<Tutorial> tut = tutorialRepository.findByPublishedAndKeyword(published, word);
        List<TutorialDto> tutDto = tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
        return tutDto;
    }
}
