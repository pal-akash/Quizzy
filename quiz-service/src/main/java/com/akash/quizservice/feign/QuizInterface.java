package com.akash.quizservice.feign;

import com.akash.quizservice.model.QuestionWrapper;
import com.akash.quizservice.model.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numOfQuestions);

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Long> questionIds);

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<UserResponse> userResponses);
}
