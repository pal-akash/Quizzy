package com.akash.questionservice.controller;

import com.akash.questionservice.model.Question;
import com.akash.questionservice.model.QuestionWrapper;
import com.akash.questionservice.model.UserResponse;
import com.akash.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestion")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id){
        return questionService.getQuestionById(id);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id){
        return questionService.deleteQuestionById(id);
    }

    @PutMapping("update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    // generate questions. quiz service will need to contact question service for generating questions instead of contacting question database itself
    // getQuestions(questionId)
    // getScore

    @GetMapping("generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numOfQuestions){
        return questionService.getQuestionsForQuiz(category, numOfQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Long> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<UserResponse> userResponses){
        return questionService.getScore(userResponses);
    }
}
