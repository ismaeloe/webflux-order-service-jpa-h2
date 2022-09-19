package mx.com.ismaeloe.exceptions;

@SuppressWarnings("unused")
public class ResourceNotFoundResponse extends NotFoundResponse {
	
	//private String resourceName;
	//private String fieldName;
	//private String fielValue;

	@SuppressWarnings("unused")
	public ResourceNotFoundResponse( String message ) {
		
		super( message != null ? message : "Resource not found");
	}	

	//final String message,
	public ResourceNotFoundResponse(final String resourceName,
									final String fieldName,
									final String fielValue)
	{
		
		/*super( (message != null ?
				message : String.format("%s not found with %s", resourceName ,fieldName))
		);*/
		super( String.format("%s not found with %s=%s", resourceName ,fieldName,fielValue));
		
		//this.resourceName = resourceName;
		//this.fieldName = fieldName;
		//this.fielValue = fielValue;
	}	

}
