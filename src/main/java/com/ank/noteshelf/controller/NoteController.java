package com.ank.noteshelf.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ank.noteshelf.resource.NoteInput;
import com.ank.noteshelf.resource.UserLoginDetail;
import com.ank.noteshelf.service.NoteService;
import com.ank.noteshelf.vo.NoteVO;

@RequestMapping("/note")
@RestController
public class NoteController {

	/**
	 * To support following api's
	 * 
	 * POST /note (create new note)
	 * 
	 * PUT /note (update existing note)
	 * 
	 * Delete /note (delete existing note)
	 */

	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	NoteService noteService;

	/**
	 * <code>createNote</code> - Create a new note from the input after validation
	 * @return ResponseEntity<NoteVO>
	 * */
	@PostMapping
	public ResponseEntity<NoteVO> createNote(@RequestBody @Valid NoteInput noteInput, HttpSession session) {
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		NoteVO noteVO = noteService.createNote(noteInput, userLoginDetail.getUserId());
		return new ResponseEntity<NoteVO>(noteVO, HttpStatus.OK);
	}

	/**
	 * <code>getAllNotes</code> - Get all the notes associated with User
	 * @return ResponseEntity<List<NoteVO>>
	 * */
	@GetMapping("/all")
	public ResponseEntity<List<NoteVO>> getAllNotes(HttpSession session) {
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		List<NoteVO> notesList = noteService.getAllNotesByUser(userLoginDetail.getUserId());
		return new ResponseEntity<List<NoteVO>>(notesList, HttpStatus.OK);
	}

	/**
	 * Validating path variable
	 * 
	 * https://stackoverflow.com/questions/19419234/how-to-validate-spring-mvc-pathvariable-values
	 * 
	 */
	
	/**
	 * <code>getNoteById</code> - Get specific note from the provided id
	 * @return ResponseEntity<NoteVO>
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<NoteVO> getNoteById(@PathVariable(value = "id") int noteId, HttpSession session) {

		NoteVO noteVO = null;
		ResponseEntity<NoteVO> response = null;

		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		noteVO = noteService.getNoteById(noteId, userLoginDetail.getUserId());

		HttpStatus status = noteVO != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		response = new ResponseEntity<>(noteVO, status);
		return response;
	}
	
	/**
	 * <code>updateNoteById</code> - Update specific note from the provided id
	 * @return ResponseEntity<NoteVO>
	 * */
	@PutMapping({"/{id}"})
	public ResponseEntity<NoteVO> updateNoteById(@PathVariable(value = "id") int noteId, @RequestBody @Valid NoteInput noteInput, HttpSession session) {
		
		ResponseEntity<NoteVO> response = null;
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		NoteVO noteVO = noteService.updateNote(noteInput, noteId, userLoginDetail.getUserId());
		response = new ResponseEntity<NoteVO>(noteVO, HttpStatus.OK);
		return response;
	}
	
	/**
	 * <code>deleteNoteById</code> - Delete specific note from the provided id
	 * @return ResponseEntity<Boolean>
	 * */
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteNoteById(@PathVariable(value = "id") int noteId, HttpSession session) {

		/**
		 * If the delete target doesn't exist, return 404(Not Found). 
		 * In some special case, the server take the delete request, 
		 * but will delete the resource in async way, 
		 * which means return client a 202(Accept) before the resource really get deleted.
		 * */
		
		Boolean isDeleted = null;
		ResponseEntity<Boolean> response = null;

		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		isDeleted = noteService.deleteNoteById(noteId, userLoginDetail.getUserId());

		HttpStatus status = isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		response = new ResponseEntity<>(isDeleted, status);
		return response;
	}
}
