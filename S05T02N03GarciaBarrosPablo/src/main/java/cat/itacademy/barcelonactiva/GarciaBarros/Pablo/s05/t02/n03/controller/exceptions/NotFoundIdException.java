package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions;

public class NotFoundIdException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5923999954098846624L;
	private static final String DESCRIPTION = "NO SE ENCONTRO EL ID BUSCADO";
	
	
	public NotFoundIdException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}


}
