package com.example.repository;

import com.example.dto.TutorialDto;
import com.example.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial,Integer> {

    public Tutorial findTutorialByTitle(String title);

    public List<Tutorial> findByTitleContainingIgnoreCase(String word);

    public List<Tutorial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String word1, String word2);

//    JPQL query
    @Query("SELECT t FROM Tutorial t WHERE t.published=:published AND ((LOWER(t.title) LIKE LOWER(CONCAT('%',:keyword,'%'))) OR (LOWER(t.description) LIKE (CONCAT('%',:keyword,'%')))) ")
    public List<Tutorial> findByPublishedAndKeyword(Boolean published, String keyword);

    public List<Tutorial> findByPublished(Boolean published);

}
