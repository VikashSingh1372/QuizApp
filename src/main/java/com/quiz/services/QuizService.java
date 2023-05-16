package com.quiz.services;


import org.springframework.http.ResponseEntity;

import com.quiz.dto.QuizDto;

public interface QuizService {
	
	ResponseEntity<Object> createQuiz(QuizDto quizDto);
	ResponseEntity<Object> allQuiz();
	ResponseEntity<Object> getActiveQuizzes();
	ResponseEntity<Object> getQuizResultById(String quizId);

}
