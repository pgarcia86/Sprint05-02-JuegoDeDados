package cat.itacademy.barcelonactiva.GarciaBarros.Pablo.s05.t02.n03.controller.exceptions;

public class NotValidIdException extends RuntimeException{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 7466004906812391082L;
		private static final String DESCRIPTION = "EL ID INGRESADO NO ES VALIDO";
		
		public NotValidIdException(String detail) {
			super(DESCRIPTION + ". " + detail);
		}

}
