package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions;

public class NotValidURIException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4716681130197218492L;
	private static final String DESCRIPTION = "LA PAGINA NO SE HA ENCONTRADO";
	
	public NotValidURIException(String detail) {
		super(DESCRIPTION + ". " + detail);
	}

}
