package com.ank.noteshelf.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ank.noteshelf.resource.NoteInput;
import com.ank.noteshelf.resource.UserLoginDetail;
import com.ank.noteshelf.service.NoteService;
import com.ank.noteshelf.validation.annotation.Numeric;
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

	@PostMapping
	public ResponseEntity<NoteVO> createNote(@RequestBody @Valid NoteInput noteInput, HttpSession session) {
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		NoteVO noteVO = noteService.createNote(noteInput, userLoginDetail.getUserId());
		return new ResponseEntity<NoteVO>(noteVO, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<NoteVO>> getAllNotes(HttpSession session) {
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		List<NoteVO> notesList = noteService.getAllNotesByUser(userLoginDetail.getUserId());
		return new ResponseEntity<List<NoteVO>>(notesList, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<NoteVO> getNoteById(@PathVariable(value = "id") int noteId, HttpSession session) {
		UserLoginDetail userLoginDetail = (UserLoginDetail) session.getAttribute("userLoginDetail");
		NoteVO noteVO = noteService.getNoteById(noteId, userLoginDetail.getUserId());

		HttpStatus status = noteVO != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<NoteVO>(noteVO, status); 
	}

}
