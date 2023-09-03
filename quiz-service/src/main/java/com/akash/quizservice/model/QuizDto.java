package com.akash.quizservice.model;

import lombok.Data;

@Data
public class QuizDto {
    String category;
    Integer numOfQuestions;
    String title;
}
