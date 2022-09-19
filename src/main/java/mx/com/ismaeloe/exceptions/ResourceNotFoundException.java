package mx.com.ismaeloe.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(final String resourceName,
									 final String fieldName,
									 final String fielValue) {

			/*super( (message != null ?
			message : String.format("%s not found with %s", resourceName ,fieldName))
			);*/
			super( String.format("%s not found with %s=%s", resourceName ,fieldName,fielValue));

	}
	
}
