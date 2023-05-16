package com.quiz.schedulers;

import com.quiz.entities.Quiz;
import com.quiz.entities.QuizStatus;
import com.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Component
public class UpdateQuizStatusScheduler {

	@Autowired
	private QuizRepository quizRepo;

	@Scheduled(cron ="@daily", zone = "Asia/Kolkata")
	public void updateQuizStatus() {

		// find All quizzes
		List<Quiz> quizList = quizRepo.findAll();
		List<Quiz> updatedQuizList = new ArrayList<>();
		Date currDate = new Date();

		if (!CollectionUtils.isEmpty(quizList)) {
			for (Quiz quiz : quizList) {

				if (Objects.nonNull(quiz.getStartDate()) && Objects.nonNull(quiz.getEndDate())) {
					// Set quizStatus based on startDate and endDate and currDate
					if (currDate.after(quiz.getStartDate()) && currDate.before(quiz.getEndDate()))
						quiz.setQuizStatus(QuizStatus.ACTIVE);
					else if (currDate.before(quiz.getStartDate())) {
						quiz.setQuizStatus(QuizStatus.INACTIVE);
					} else if (currDate.after(quiz.getEndDate())) {
						quiz.setQuizStatus(QuizStatus.FINISHED);
					}

					updatedQuizList.add(quiz);
				}
			}

			if (updatedQuizList.size() > 0) {
				quizRepo.saveAll(updatedQuizList);
			}
		}

	}

}
