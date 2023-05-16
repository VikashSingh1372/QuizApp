package com.quiz.repository;

import com.quiz.entities.QuizStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quiz.entities.Quiz;

import java.util.List;

public interface QuizRepository  extends JpaRepository<Quiz, Integer> {
    List<Quiz> findAllByQuizStatus(QuizStatus quizStatus);

}
