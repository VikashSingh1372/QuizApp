package com.quiz.entities;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String question;

	@ElementCollection(targetClass=String.class) 
	private List<String> options;

	private int rightAnswer;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
	private Date startDate;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
	private Date endDate;

	private QuizStatus quizStatus;

	private String result;

}
