package mx.com.ismaeloe.exceptions;

import org.springframework.http.HttpStatus;

//public just valid for exceptions 
public class BadRequestResponse extends BaseErrorResponse {
	
	//Constructor
	public BadRequestResponse( String message ) {
	
		super(HttpStatus.BAD_REQUEST , message);	
	}
	
}
