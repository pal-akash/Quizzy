package com.akash.questionservice.service;

import com.akash.questionservice.dao.QuestionDao;
import com.akash.questionservice.model.Question;
import com.akash.questionservice.model.QuestionWrapper;
import com.akash.questionservice.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Question> getQuestionById(Long id) {
        try {
            return new ResponseEntity<>(questionDao.findById(id).get(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Question(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDao.save(question);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed", HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<String> deleteQuestionById(Long id) {
        try{
            questionDao.deleteById(id);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("No such content", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<String> updateQuestion(Question question) {
        Optional<Question> foundQuestionOptional = questionDao.findById(question.getId());
        if(foundQuestionOptional.isEmpty()){
            return new ResponseEntity<>("No such content", HttpStatus.NO_CONTENT);
        }
        questionDao.save(question);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<List<Long>> getQuestionsForQuiz(String category, Integer numOfQuestions) {
        List<Long> questionsForQuiz = questionDao.findRandomQuestions(category, numOfQuestions);
        return new ResponseEntity<>(questionsForQuiz, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Long> questionIds) {
        List<QuestionWrapper> wrappedQuestions = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        for(Long id : questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper(
                    question.getId(),
                    question.getQuestionTitle(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4()
            );
            wrappedQuestions.add(wrapper);
        }
        return new ResponseEntity<>(wrappedQuestions, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<UserResponse> userResponses) {

        int right = 0;
        for(UserResponse userResponse : userResponses){
            Question question = questionDao.findById(userResponse.getId()).get();
            if(userResponse.getResponse().equals(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
