package com.quiz.dto;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {

    private String question;
    private List<String> options;

    private Integer rightAnswer;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private Date startDate;
    
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private Date endDate;

}
