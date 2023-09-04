package com.akash.quizservice.service;

import com.akash.quizservice.dao.QuizDao;
import com.akash.quizservice.feign.QuizInterface;
import com.akash.quizservice.model.QuestionWrapper;
import com.akash.quizservice.model.Quiz;
import com.akash.quizservice.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;


    public ResponseEntity<String> createQuiz(String category, Integer numQ, String title) {

        List<Long> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Long id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        if(quiz.isEmpty()){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
        }
        List<Long> questionIds = quiz.get().getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionsForUser = quizInterface.getQuestionsFromId(questionIds);

        return questionsForUser;
    }

    public ResponseEntity<Integer> calculateResult(Long id, List<UserResponse> userResponses) {
        ResponseEntity<Integer> score = quizInterface.getScore(userResponses);
        return score;
    }
}
