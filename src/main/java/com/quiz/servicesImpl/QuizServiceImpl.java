package com.quiz.servicesImpl;

import java.util.*;
import com.quiz.entities.QuizStatus;
import com.quiz.exceptions.ResourceNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.dto.QuizDto;
import com.quiz.entities.Quiz;
import com.quiz.repository.QuizRepository;
import com.quiz.services.QuizService;
import org.springframework.util.CollectionUtils;
import static com.quiz.entities.QuizStatus.ACTIVE;
import static java.util.Objects.nonNull;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepo;

	@Override
	public ResponseEntity<Object> createQuiz(QuizDto quizDto) {
		System.out.print("the date is" + quizDto.getStartDate());

		Quiz quiz;
		if (nonNull(quizDto)) {
			quiz = new Quiz();
			// id is auto generated

			// set questions
			if (StringUtils.isNotBlank(quizDto.getQuestion()))
				quiz.setQuestion(quizDto.getQuestion());
			else
				return new ResponseEntity<>("Question should not be empty/null/blank", HttpStatusCode.valueOf(400));

			// set options
			if (!CollectionUtils.isEmpty(quizDto.getOptions()))
				quiz.setOptions(quizDto.getOptions());
			else
				return new ResponseEntity<>("Options should not be empty/null", HttpStatusCode.valueOf(400));

			// set rightAnswerIndex
			if (nonNull(quizDto.getRightAnswer()) && quizDto.getRightAnswer() < quizDto.getOptions().size())
				quiz.setRightAnswer(quizDto.getRightAnswer());
			else
				return new ResponseEntity<>(
						"RightAnswer Index should not be empty/null " + "or should be pointing to the options element",
						HttpStatusCode.valueOf(400));

			// set result based on rightAnswerIndex
			if (nonNull(quizDto.getRightAnswer()) && quizDto.getRightAnswer() < quizDto.getOptions().size())
				quiz.setResult(quizDto.getOptions().get(quizDto.getRightAnswer()));
			else
				return new ResponseEntity<>("RightAnswer Index  should be pointing to the options element",
						HttpStatusCode.valueOf(400));
			// set startDate
			if (Objects.nonNull(quizDto.getStartDate()))
				quiz.setStartDate(quizDto.getStartDate());
			else
				return new ResponseEntity<>("StartDate should not be empty/null", HttpStatusCode.valueOf(400));

			// set endDate
			if (Objects.nonNull(quizDto.getEndDate()))
				quiz.setEndDate(quizDto.getEndDate());
			else
				return new ResponseEntity<>("EndDate should not be empty/null ", HttpStatusCode.valueOf(400));

			// Set quizStatus based on startDate and endDate and currDate
			Date currDate = new Date();

			if ((Objects.nonNull(quizDto.getStartDate())) && (Objects.nonNull(quizDto.getEndDate()))
					&& (Objects.nonNull(currDate))) {

				if (currDate.after(quiz.getStartDate()) && currDate.before(quiz.getEndDate())) {
					quiz.setQuizStatus(QuizStatus.ACTIVE);
				} else if (currDate.before(quiz.getStartDate())) {
					quiz.setQuizStatus(QuizStatus.INACTIVE);
				} else if (currDate.after(quiz.getEndDate())) {
					quiz.setQuizStatus(QuizStatus.FINISHED);
				}
			} else {
				return new ResponseEntity<>("Date should not be empty/null ", HttpStatusCode.valueOf(400));
			}

			// persist in db
			Quiz saveQuiz = quizRepo.save(quiz);
			return new ResponseEntity<>(saveQuiz, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
		}

	}
	// active Quizzes

	@Override
	public ResponseEntity<Object> getActiveQuizzes() {
		// this need to check
		List<Quiz> activeQuizzes = quizRepo.findAllByQuizStatus(ACTIVE);

		if (!CollectionUtils.isEmpty(activeQuizzes))
			return new ResponseEntity<>(activeQuizzes, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// get quiz result by id
	@Override
	public ResponseEntity<Object> getQuizResultById(String quizId) {

		if (StringUtils.isNotBlank(quizId)) {
			Quiz quiz = quizRepo.findById(Integer.valueOf(quizId)).orElseThrow(() -> new ResourceNotFoundException("quiz", "Id", quizId));;

			if (nonNull(quiz))
				return new ResponseEntity<>(quiz.getResult(), HttpStatus.OK);

			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} else {
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}

	}

	// all quiz
	@Override
	public ResponseEntity<Object> allQuiz() {
		List<Quiz> allQuiz = this.quizRepo.findAll();

		if (!CollectionUtils.isEmpty(allQuiz))
			return new ResponseEntity<>(allQuiz, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
