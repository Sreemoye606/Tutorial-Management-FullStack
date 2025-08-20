package com.example.repository;

import com.example.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial,Integer> {

    public Tutorial findTutorialByTitleIgnoreCase(String title); //for checking duplicate titles in new add & update

    public List<Tutorial> findByTitleContainingIgnoreCase(String word); // for search

    public List<Tutorial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String word1, String word2); //for search

//    JPQL query
    @Query("SELECT t FROM Tutorial t WHERE t.published=:published AND ((LOWER(t.title) LIKE LOWER(CONCAT('%',:keyword,'%'))) OR (LOWER(t.description) LIKE (CONCAT('%',:keyword,'%')))) ")
    public List<Tutorial> findByPublishedAndKeyword(Boolean published, String keyword); //for search

    public List<Tutorial> findByPublished(Boolean published); //for search

}
