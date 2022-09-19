package mx.com.ismaeloe.exceptions;

import org.springframework.http.HttpStatus;

public class ServerErrorResponse extends BaseErrorResponse{
	
	public ServerErrorResponse( String message) {
		super(HttpStatus.INTERNAL_SERVER_ERROR , message );
	}
	
	
}
