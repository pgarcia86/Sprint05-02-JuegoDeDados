package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions;

public class CannotDeleteException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION = "NO SE PUEDE ELIMINAR LAS TIRADAS";
	
	public CannotDeleteException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
