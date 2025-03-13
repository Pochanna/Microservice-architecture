/**
 * 
 */
package com.pochanna.question.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pochanna.question.dao.QuestionDao;
import com.pochanna.question.model.Question;
import com.pochanna.question.model.QuestionWrapper;
import com.pochanna.question.model.Response;

/**
 * 
 */
@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;
	
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<List<Question>>(questionDao.findAll(), HttpStatus.OK) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST) ;

	}

	public ResponseEntity<List<Question>> findByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> save(Question question) {
		 try {
			questionDao.save(question);
		       return new ResponseEntity<String>("Success", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);	}

	    public ResponseEntity<List<Integer>> getQuestionsFromQuiz(String category, int numberOfquestions) {
	    	try {
				new ResponseEntity<>(questionDao.findRandomQuestionsByCategory(category, numberOfquestions),HttpStatus.OK) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		  return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST) ;

	       }

		public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> quentionsIds) {
             try {
				List<Question> questionsList= questionDao.findAllById(quentionsIds);
				 List<QuestionWrapper> qustW= new ArrayList<QuestionWrapper>();
				 for (Question question : questionsList) {
					 QuestionWrapper w = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(),  question.getOption2(),  question.getOption3(),  question.getOption4());
					 qustW.add(w);
				}
				return new ResponseEntity<List<QuestionWrapper>>(qustW,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
				return new ResponseEntity<List<QuestionWrapper>>(new ArrayList<QuestionWrapper>(),HttpStatus.BAD_REQUEST);

		}

		public ResponseEntity<Integer> getscore(List<Response> responses) {
			try {
				int result = 0;
				for (Response response : responses) {
					Optional<Question> quest = questionDao.findById(response.getId());
					if(quest !=null && quest.get().getRightAnswer() !=null &&quest.get().getRightAnswer().equalsIgnoreCase(response.getResponse())) {
						result ++;
					}
				}
				return new ResponseEntity<Integer>(result,HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<Integer>(0,HttpStatus.BAD_REQUEST);

		}

}
