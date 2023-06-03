package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions;

public class ErrorMessage {
	
	private String exception;
	private String message;
	
	public ErrorMessage(Exception e) {
		this.exception = e.getClass().getSimpleName();
		this.message = e.getMessage();
		
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	@Override
	public String toString() {
		return "ErrorMessage {exception = " + this.exception + "\\ message = " + this.message;
	}

}
