package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class DiceGameExceptionController extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleException(NotFoundIdException e){
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		return buildResponseEntity(httpStatus, e);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleException(NotValidIdException e){
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, e);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleException(CannotDeleteException e){
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return buildResponseEntity(httpStatus, e);
	}
	
	private ResponseEntity<ErrorMessage> buildResponseEntity(HttpStatus httpStatus, Exception e){
		
		ErrorMessage error = new ErrorMessage(e);
		return new ResponseEntity<>(error, httpStatus);
	}

}
