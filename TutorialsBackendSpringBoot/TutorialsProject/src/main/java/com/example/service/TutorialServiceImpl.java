package com.example.service;

import com.example.dto.TutorialDto;
import com.example.entity.Tutorial;
import com.example.exception.DuplicateResourceException;
import com.example.exception.InvalidRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.TutorialMapper;
import com.example.repository.TutorialRepository;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Tutorial org = tutorialRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Tutorial with id "+id+" not found"));
        return TutorialMapper.createDto(org);
    }

    @Override
    public TutorialDto saveTutorial(TutorialDto tutorialDto) {
        Tutorial org = TutorialMapper.createOriginal(tutorialDto);
        Tutorial existing = tutorialRepository.findTutorialByTitleIgnoreCase(tutorialDto.getTitle().trim());
        if (existing != null) {
            throw new DuplicateResourceException("Tutorial with this title already exists");
        }
        else if (org.getTitle().trim().isEmpty()) {
            throw new InvalidRequestException("Tutorial title cannot be empty");
        }
        else if (org.getDescription().trim().isEmpty()) {
            throw new InvalidRequestException("Tutorial description cannot be empty");
        }
        else {
            Tutorial saved = tutorialRepository.save(org);
            return TutorialMapper.createDto(saved);
        }

    }

    @Override
    public TutorialDto updateTutorial(int id, TutorialDto tutorialDto) {
        Tutorial org = tutorialRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Tutorial with id "+id+" not found"));

        Tutorial existing = tutorialRepository.findTutorialByTitleIgnoreCase(tutorialDto.getTitle().trim());


        if (tutorialDto.getTitle().trim().isEmpty()) {
            throw new InvalidRequestException("Tutorial title cannot be empty");
        }
        else if (tutorialDto.getDescription().trim().isEmpty()) {
            throw new InvalidRequestException("Tutorial description cannot be empty");
        }
        else if (existing != null && existing.getId() != id) {
            throw new DuplicateResourceException("Tutorial with this title already exists");
        }
        else {
            org.setTitle(tutorialDto.getTitle());
            org.setDescription(tutorialDto.getDescription());
            org.setPublished(tutorialDto.getPublished());
            tutorialRepository.save(org);
            return TutorialMapper.createDto(org);
        }
    }

    @Override
    public TutorialDto deleteTutorial(int id) {
        Tutorial org = tutorialRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Tutorial with id "+id+" not found"));
        tutorialRepository.deleteById(id);
        return TutorialMapper.createDto(org);
    }

// query methods

    @Override
    public List<TutorialDto> findByTitleContainingIgnoreCase(String word) {
        List<Tutorial> tut = tutorialRepository.findByTitleContainingIgnoreCase(word);
        if (tut.isEmpty()) {
            throw new ResourceNotFoundException("No tutorials found");
        }
        else {
            List<TutorialDto> tutDto = tut.stream().map((t) -> TutorialMapper.createDto(t)).toList();
            return tutDto;
        }
    }

    @Override
    public List<TutorialDto> findByPublished(Boolean published) {
        List<Tutorial> tut = tutorialRepository.findByPublished(published);
        List<TutorialDto> tutDto= tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
        return tutDto;
    }

    @Override
    public List<TutorialDto> findByTitleOrDescContainingIgnoreCase(String word) {
        List<Tutorial> tut = tutorialRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(word, word);
        if (tut.isEmpty()) {
            throw new ResourceNotFoundException("No tutorials found");
        }
        else{
            List<TutorialDto> tutDto = tut.stream().map((t)->TutorialMapper.createDto(t)).toList();
            return tutDto;
        }
    }

    @Override
    public List<TutorialDto> findByPublishedAndKeyword(Boolean published,String word) {
        List<Tutorial> tut = tutorialRepository.findByPublishedAndKeyword(published, word);
        if (tut.isEmpty()) {
            throw new ResourceNotFoundException("No tutorials found");
        }
        else {
            List<TutorialDto> tutDto = tut.stream().map((t) -> TutorialMapper.createDto(t)).toList();
            return tutDto;
        }
    }
}
