/**
 * 
 */
package com.pochanna.question.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pochanna.question.model.Question;
import com.pochanna.question.model.QuestionWrapper;
import com.pochanna.question.model.Response;
import com.pochanna.question.service.QuestionService;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 */
@RestController
@RequestMapping("questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getQuestion() {

		return questionService.getAllQuestions();
	}

	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {

		return questionService.findByCategory(category);
	}

	@PostMapping("addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.save(question);
	}

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsFromQuiz(@RequestParam String category, int numberOfquestions) {
		return questionService.getQuestionsFromQuiz(category, numberOfquestions);
	}

	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(@RequestBody List<Integer> quentionsIds) {
		return questionService.getQuestionsFromIds(quentionsIds);
	}

	@PostMapping("getScore")
	public ResponseEntity<Integer> getscore(@RequestBody List<Response> responses) {
		return questionService.getscore(responses);
	}

	// generate
	// get questions
	// get score

}
