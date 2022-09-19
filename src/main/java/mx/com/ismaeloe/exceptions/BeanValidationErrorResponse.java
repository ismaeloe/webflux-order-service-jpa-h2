package mx.com.ismaeloe.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

//public just valid for exceptions
public class BeanValidationErrorResponse extends BadRequestResponse {

	//@SuppressWarnings("unused")
	private final List<BeanViolationError> errors;

	public BeanValidationErrorResponse(List<BeanViolationError> errors) {
		
		super(HttpStatus.BAD_REQUEST.getReasonPhrase());
		this.errors = (errors != null ? errors : new ArrayList<BeanViolationError>());
	}
	
	public List<BeanViolationError> getErrors(){
		return errors;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.getClass().getName())
				.append('=')
				.append(super.toString())
				.append("|errors=")
				.append(this.getErrors());
	return builder.toString();
	}
}
