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
//        Optional<Quiz> quiz = quizDao.findById(id);
//        List<Question> questionsFromDB = quiz.get().getQuestionsList();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
//
//        for(Question q : questionsFromDB){
//            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
//            questionsForUser.add(qw);
//        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Long id, List<UserResponse> userResponses) {
//        Quiz quiz = quizDao.findById(id).get();
//        List<Question> questions = quiz.getQuestionsList();
//
        int right = 0;
//        int i = 0;
//        for(UserResponse userResponse : userResponses){
//            if(userResponse.getResponse().equals(questions.get(i).getRightAnswer())){
//                right++;
//            }
//            i++;
//        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
