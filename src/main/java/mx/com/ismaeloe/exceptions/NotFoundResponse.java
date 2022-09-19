package mx.com.ismaeloe.exceptions;

import org.springframework.http.HttpStatus;
 
public class NotFoundResponse extends BaseErrorResponse{
	
	public NotFoundResponse( String message) {
		super(HttpStatus.NOT_FOUND , message );
	}
	
	
}
