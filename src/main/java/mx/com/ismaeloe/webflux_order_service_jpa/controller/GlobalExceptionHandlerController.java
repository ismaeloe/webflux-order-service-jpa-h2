package mx.com.ismaeloe.webflux_order_service_jpa.controller;

import java.nio.charset.MalformedInputException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import lombok.extern.slf4j.Slf4j;
import mx.com.ismaeloe.exceptions.BadRequestResponse;
import mx.com.ismaeloe.exceptions.BaseErrorResponse;
import mx.com.ismaeloe.exceptions.BeanViolationError;
import mx.com.ismaeloe.exceptions.BeanValidationErrorResponse;
import mx.com.ismaeloe.exceptions.NotFoundResponse;
import mx.com.ismaeloe.exceptions.ResourceNotFoundException;
import mx.com.ismaeloe.exceptions.ServerErrorResponse;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {

	//NA private final String CODE_JsonParseException = "400.1";


	/*OK IllegalArgumentException
	 * 
	 * Test HTTP GET "/order-api/v1/illegal"
	 * Using @ExceptionHandler mx.com.ismaeloe.webflux_order_service_jpa.controller.GlobalExceptionHandlerController#illegalArgumentException
	 * WARN IllegalArgumentException: |f1e07056-f727-4dd5-bd65-8e13d8f47250|throw new IllegalArgumentException
	 * INFO BadRequestResponse: |trace_id=f1e07056-f727-4dd5-bd65-8e13d8f47250|status=400, message=throw new IllegalArgumentException
	 * {
	    "message": "throw new IllegalArgumentException",
	    "timestamp": "2022-09-12T12:48:43.9673594-05:00",
	    "trace_id": "f1e07056-f727-4dd5-bd65-8e13d8f47250",
	    "status": 400
	    }
	 *
	 *  OK NoSuchElementException
	 *  HTTP GET "/order-api/v1/NoSuchElementException"
	 *  Using @ExceptionHandler ..GlobalExceptionHandlerController#handleIllegalArgumentNoSuchElementException(RuntimeException)
	 *  {
		    "message": "No value present",
		    "timestamp": "2022-09-12T14:19:41.7408848-05:00",
		    "trace_id": "8e2b5b88-b0b2-4d10-95e8-4d6d131cce88",
		    "status": 400
		}  
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class ,NoSuchElementException.class})
	public BadRequestResponse handleIllegalArgumentNoSuchElementException(RuntimeException  ex) {
		
		BadRequestResponse response = new BadRequestResponse(ex.getMessage());
		
		this.log(response ,ex);
		return response;
	}

	/* OK DataAccessResourceFailureException
	 * For Database disconnection
	 * Data access exception thrown when a resource fails completely: for example, if we can't connect to a database using JDBC.
	 * 
	 * BEFORE
	 * DEBUG findOne using query: { "_id" : "6317d805295b0843081d465bx"} fields: {} in db.collection: productsdb.product
	 *  Resolved [DataAccessResourceFailureException: Timed out after 30000 ms while waiting for a server
	 *  500 Server Error for HTTP GET "/product-api/v1/6317d805295b0843081d465bx"
	 *  at org.springframework.data.mongodb.core.MongoExceptionTranslator.translateExceptionIfPossible(MongoExceptionTranslator.java:95)
	 *  Caused by: com.mongodb.MongoTimeoutException: Timed out after 30000 ms while waiting for a server
	 *  Completed 500 INTERNAL_SERVER_ERROR
	 *  
	 *  ORIGINAL
	 *  {
		    "timestamp": "2022-09-12T05:39:48.863+00:00",
		    "path": "/product-api/v1/6317d805295b0843081d465bX",
		    "status": 500,
		    "error": "Internal Server Error",
		    "message": "Timed out after 30000 ms while waiting for a server that matches ReadPreferenceServerSelector{readPreference=primary}.
		    			Client view of cluster state is {type=UNKNOWN, servers=[{address=localhost:27018, type=UNKNOWN, state=CONNECTING,
		    			exception={com.mongodb.MongoSocketOpenException: Exception opening socket},
		    			caused by {io.netty.channel.AbstractChannel$AnnotatedConnectException: 
		    			Connection refused: no further information: localhost/0:0:0:0:0:0:0:1:27018}, 
		    			caused by {java.net.ConnectException: Connection refused: no further information}}]; 
		    			nested exception is com.mongodb.MongoTimeoutException: Timed out after 30000 ms while waiting for a server that matches ReadPreferenceServerSelector{readPreference=primary}. Client view of cluster state is {type=UNKNOWN, servers=[{address=localhost:27018, type=UNKNOWN, state=CONNECTING, exception={com.mongodb.MongoSocketOpenException: Exception opening socket},
		    			caused by {io.netty.channel.AbstractChannel$AnnotatedConnectException: Connection refused: no further information: localhost/0:0:0:0:0:0:0:1:27018},
		    		 	caused by {java.net.ConnectException: Connection refused: no further information}}]",
		    "requestId": "dff1b8ca-2"
		}
		
		AFTER PERFECT
		DEBUG findOne using query: { "_id" : "6317d805295b0843081d465bX"}
		Using @ExceptionHandler mx.com.ismaeloe.productservicerxmongo.controller.GlobalExceptionHandlerController#handleDataAccessResourceFailureException(DataAccessResourceFailureException)
		NEW
		{
		    "message": "DataBase is not Available",
		    "timestamp": "2022-09-12T01:48:01.4722523",
		    "trace_id": "47c32c06-5c75-41e3-800a-ec94d589fb89",
		    "status": 500
		}
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR )
	@ExceptionHandler(DataAccessResourceFailureException.class)
	public ServerErrorResponse handleDataAccessResourceFailureException(DataAccessResourceFailureException ex) {
		
		ServerErrorResponse response = new ServerErrorResponse("DataBase is not Available");

		this.logStackTrace(response, ex);
		return response;
	}
	
	/*OK ResourceNotFoundException(String message)
	 *   ResourceNotFoundException(String resourceName ,final String fieldName, final String fielValue)
	 * PARAMS resourceName =/product-api/v1  +  fieldName =ID  +  fielValue =6317d805295b0843081d465bX
	 * {
		    "message": "/product-api/v1 not found with id=6317d805295b0843081d465bX",
		    "timestamp": "2022-09-12T02:02:16.0992622",
		    "trace_id": "83602dc6-af78-4199-af0b-d13ad4548fdf",
		    "status": 404
		}
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public NotFoundResponse notFoundException(ResourceNotFoundException ex) {
		
		NotFoundResponse response =new NotFoundResponse(ex.getMessage());
		
		this.log(response, ex);
		return response;
	}

	
	/* NO SE INTERCEPTA en el @RestControllerAdvice
	 * org.springframework.web.server.ResponseStatusException
	 * 
	 * Resolved [ResponseStatusException: 404 NOT_FOUND] for HTTP GET /order-api/v1/NoSuchElementExceptionX
	 * Completed 404 NOT_FOUND
	 * ORIGINAL PAYLOAD
	 * {
		    "timestamp": "2022-09-12T19:26:44.488+00:00",   Regresa 5 horas mas Tarde GMT-0
		    "path": "/order-api/v1/NoSuchElementExceptionx",
		    "status": 404,
		    "error": "Not Found",
		    "message": null,
		    "requestId": "693ad180-2"
		}
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(org.springframework.web.server.ResponseStatusException.class) 
	public NotFoundResponse handleResponseStatusException(ResponseStatusException ex) {
		
		NotFoundResponse response =new NotFoundResponse(ex.getMessage());
		
		this.log(response, ex);
		return response;
	} */
	
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 @ExceptionHandler(ConstraintViolationException.class)	//OK ConstraintViolationException  Thrown By Hibernate
//ok BeanValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
//ok public ResponseEntity<BeanValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
	 public ResponseEntity<?> 							handleConstraintViolationException(ConstraintViolationException ex) {
	        log.info("ConstraintViolationException : {}", ex);
	     
	        List<BeanViolationError> errors = ex.getConstraintViolations()
	                .stream()
	                .map(constraintViolation -> {
	                    return new BeanViolationError(	constraintViolation.getRootBeanClass().getName() ,
	                    								constraintViolation.getPropertyPath().toString(),
	                    								constraintViolation.getMessage() );
	                }).collect(Collectors.toList());

	        //String message = messageSource.getMessage("invalid.data.message", null, LocaleContextHolder.getLocale());
	        BeanValidationErrorResponse response = new BeanValidationErrorResponse(errors);

	        //return ResponseEntity.ok().body(Void.class);
	        //ResponseEntity<BeanValidationErrorResponse> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); //responseEntity;
	    }
	 
	/* OK WebExchangeBindException  Thrown By Spring
	 * A specialization of ServerWebInputException thrown when after data binding and validation failure. Implements BindingResult (and its super-interface Errors) to allow for direct analysis of binding and validation errors.
	 * Since: 5.0
	 *  Thrown when after data binding and validation failure.
	 *   Implements {@link BindingResult} (and its super-interface {@link Errors})
	 * 
	 * PAYLOAD:
		"timestamp": "2022-09-09T06:52:15.537+00:00",
	    "path": "/order-api/v1",
	    "status": 400,
	    "error": "Bad Request",
	    "message": Validation failed for argument at index 0 in method: 
	 * 	public reactor.core.publisher.Mono<PurchaseOrderResponseDto>
	 *   controller.PurchaseOrderController.createOrder(reactor.core.publisher.Mono<PurchaseOrderRequestDto>),
	 *    with 1 error(s): [Field error in object 'purchaseOrderRequestDtoMono' on field 'idProduct': rejected value []; 
	 *     codes [NotEmpty.purchaseOrderRequestDtoMono.idProduct,NotEmpty.idProduct,NotEmpty.java.lang.String,NotEmpty]; 
	 *     arguments [org.springframework.context.support.DefaultMessageSourceResolvable:
	 *      codes [purchaseOrderRequestDtoMono.idProduct,idProduct]; arguments []; default message [idProduct]];
	 *       default message [002.2,PRODUCT,Must Introduce a Product]]  
    "requestId": "450f8a9d-1",
    "errors": [{
            "codes": [
                "NotEmpty.purchaseOrderRequestDtoMono.idProduct",
                "NotEmpty.idProduct",
                "NotEmpty.java.lang.String",
                "NotEmpty"],
            "arguments": [{
                    "codes": [
                        "purchaseOrderRequestDtoMono.idProduct",
                        "idProduct" ],
                    "arguments": null,
                    "defaultMessage": "idProduct",
                    "code": "idProduct"
                }],
            "defaultMessage": "1002,Must Introduce a Product",
            "objectName": "purchaseOrderRequestDtoMono",
            "field": "idProduct",
            "rejectedValue": "",
            "bindingFailure": false,
            "code": "NotEmpty"
        },
        {
            "codes": [
                "Min.purchaseOrderRequestDtoMono.idCustomer",
                "Min.idCustomer",
                "Min.java.lang.Integer",
                "Min"
            ],
            "arguments": [
                {
                    "codes": [
                        "purchaseOrderRequestDtoMono.idCustomer",
                        "idCustomer"
                    ],
                    "arguments": null,
                    "defaultMessage": "idCustomer",
                    "code": "idCustomer"
                },
                1
            ],
            "defaultMessage": "1001,Customer must be greater than Cero",
            "objectName": "purchaseOrderRequestDtoMono",
            "field": "idCustomer",
            "rejectedValue": 0,
            "bindingFailure": false,
            "code": "Min"
        } ]}
        
        CHANGE TO
        { "message": "Bad Request",
	    "timestamp": "2022-09-09T01:57:19.0195593",
	    "trace_id": "4af4850f-d5c1-488c-849d-6201819ef7ab",
	    "status": 400 ,
	    "errors": [
	        {
	            "code": "1002",
	            "field": "idProduct",
	            "error": "Must Introduce a Product"
	        },
	        {
	            "code": "1001",
	            "field": "idCustomer",
	            "error": "Customer must be greater than Cero"
	        }]
	     }   

	  * FAIL MethodArgumentNotValidException.class
	  * Exception to be thrown when validation on an argument annotated with {@code @Valid} fails.
	  * Extends {@link BindException} as of 5.3
	  * 
	  * WebFlux does not Catch MethodArgumentNotValidException  and Go to ServerWebInputException
	  * 
	  * Example:
	  * @ExceptionHandler(MethodArgumentNotValidException.class )
	  * public BeanValidationErrorResponse handleBindingException(MethodArgumentNotValidException ex) {
	  * 
	  * OK WebExchangeBindException  Thrown By Spring
	 * A specialization of ServerWebInputException thrown when after data binding and validation failure. Implements BindingResult (and its super-interface Errors) to allow for direct analysis of binding and validation errors.
	 * Since: 5.0
	 *  Thrown when after data binding and validation failure.
	 *   Implements {@link BindingResult} (and its super-interface {@link Errors})
	 * 
	 * OK Option 1: @ResponseStatus(HttpStatus.BAD_REQUEST)
	 				public BeanValidationErrorResponse handleBindingException(WebExchangeBindException ex) {
	 * OK Option 2: WithOut @ResponseStatus(HttpStatus.BAD_REQUEST)
					public ResponseEntity<BeanValidationErrorResponse> handleBindingException(WebExchangeBindException ex) {
	 * OK Option 3: WithOut @ResponseStatus(HttpStatus.BAD_REQUEST)
					public ResponseEntity<?> handleBindingException(WebExchangeBindException ex) { 
	 */
   //@ExceptionHandler(MethodArgumentNotValidException.class )
	 @ExceptionHandler(WebExchangeBindException.class)
	 public ResponseEntity<?> handleBindingException(WebExchangeBindException ex) {
		
		log.warn(ex.getBindingResult().toString());
		
		BindingResult result = ex.getBindingResult();

		//"result.getFieldError() =Field error in object 'purchaseOrderRequestDtoMono' on field 'idProduct': rejected value []; codes [NotEmpty.purchaseOrderRequestDtoMono.idProduct,NotEmpty.idProduct,NotEmpty.java.lang.String,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [purchaseOrderRequestDtoMono.idProduct,idProduct];
		// arguments []; default message [idProduct]]; default message [1002,Must Introduce a Product]
		System.err.println("result.getFieldError() =" + result.getFieldError());
		
	    //OK Get List of BeanViolationError
		List<BeanViolationError> errors = result
	    					.getFieldErrors()
	    					.stream()
	    					.map( fieldError ->{
	    						System.err.println("fieldError.getField() =" + fieldError.getField()); 				//idProduct
	    						System.err.println("fieldError.getObjectName() =" + fieldError.getObjectName());	//purchaseOrderRequestDtoMono
	    						System.err.println("fieldError.getRejectedValue() =" + fieldError.getRejectedValue()); 	//EMPTY
	    						System.err.println("fieldError.getDefaultMessage() =" + fieldError.getDefaultMessage());//1002,Must Introduce a Product
	    				
	    						String[] aText = fieldError.getDefaultMessage().split(",");
	    						String code = (aText.length == 2 ? aText[0] : "");   
	    						String msgError = (aText.length == 2 ? aText[1] : fieldError.getDefaultMessage());
	    					
								BeanViolationError beanVal = new BeanViolationError(code ,fieldError.getField() ,msgError );
	    						return beanVal;
		                }).collect(Collectors.toList());

		/*OK Get Map of Error Messages
		List<Map<String,String>> errors = result
			    					.getFieldErrors()
			    					.stream()
			    					.map( fieldError ->{
					                    Map<String,String>  error =  new HashMap<>();
					                    					error.put(err.getField(), err.getDefaultMessage());
					                    return error;
				                }).collect(Collectors.toList());
		*/

		/*OK Get List of Error Messages
		List<String> message = result
			.getAllErrors()
			.stream()
			.peek(objError -> {
				//String[] aText = objError.getDefaultMessage().split(",");
				System.err.println("objError.getCode() =" + objError.getCode() );					//NotEmpty		
				System.err.println("objError.getObjectName() =" + objError.getObjectName() );		//purchaseOrderRequestDtoMono
				System.err.println("objError.getDefaultMessage() =" + objError.getDefaultMessage());//1002,Must Introduce a Product
			}).map(objError -> {
				String msg = objError.getDefaultMessage(); 
				return msg;
			}).collect(Collectors.toList());
		
		System.err.println("message =" + message );	//[1002,Must Introduce a Product]
		*/

		ex.getBindingResult().getAllErrors()
			.forEach( objError -> {
				System.err.println("objError =" + objError);	//Field error in object 'purchaseOrderRequestDtoMono' on field 'idProduct': rejected value []; codes [NotEmpty.purchaseOrderRequestDtoMono.idProduct,NotEmpty.idProduct,NotEmpty.java.lang.String,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [purchaseOrderRequestDtoMono.idProduct,idProduct]; arguments []; default message [idProduct]]; default message [1002,Must Introduce a Product]
				System.err.println("objError.getCode() =" + objError.getCode() );				//NotEmpty
				System.err.println("objError.getObjectName() =" + objError.getObjectName() ); 	//purchaseOrderRequestDtoMono
				System.err.println("objError.getDefaultMessage() =" + objError.getDefaultMessage()); //002.2,PRODUCT,Must Introduce a Product
					
				/*
				 * code =NotEmpty.purchaseOrderRequestDtoMono.idProduct
				 * code =NotEmpty.idProduct
				 * code =NotEmpty.java.lang.String
				 * code =NotEmpty
				 */
				for(String s : objError.getCodes() ) {
					System.err.println("code =" + s);
				}
				
				//obj =org.springframework.context.support.DefaultMessageSourceResolvable:
				// codes [purchaseOrderRequestDtoMono.idProduct,idProduct]; arguments []; default message [idProduct]
				for(Object obj : objError.getArguments()  ) {
					System.err.println("obj =" + obj);
				}
				
				/*
				 * msg =NotEmpty.purchaseOrderRequestDtoMono.idProduct
				 * msg =NotEmpty.idProduct
				 * msg =NotEmpty.java.lang.String
				 * msg =NotEmpty
				 */
				for(String m : objError.getCodes() ) {
					System.err.println("msg =" + m);
				}
			});
	
		BeanValidationErrorResponse response = new BeanValidationErrorResponse(errors);
		
		log(response ,ex);
		
		//OK return response; Option 1
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  //Option 2 
	}
	
	 /* ServerWebInputException
	 * Exception for errors that fit response status 400 (bad request)
	 * 
	 * Resolved [ServerWebInputException: by default return 400 (bad request)
	 * Message:
	 * 400 BAD_REQUEST "Failed to read HTTP message";
	 *  nested exception is org.springframework.core.codec.DecodingException:
	 *  JSON decoding error:
	 *  Unexpected character ('"' (code 34)): was expecting comma to separate Object entries;
	 *  nested exception is com.fasterxml.jackson.core.JsonParseException:
	 *  Unexpected character ('"' (code 34)): was expecting comma to separate Object entries
	 *  for HTTP POST /order-api/v1
	 *  Encoding [path=/order-api/v1, status=400, error=Bad Request, message= (truncated)...]
	 *  Completed 400 BAD_REQUEST
	 * 
	 * BadRequestResponse return
	 * {
     * "message": "Validation failure",
     * "timestamp": "2022-09-09T17:25:08.6476767",
     * "trace_id": "5b7aac56-8811-4051-a535-cf471d6fb931",
     * "status": 400
	 * }
	 * 
	 * {
	 * "message": "Bad Request",
	 * "timestamp": "2022-09-09T17:54:14.9167637",
	 * "trace_id": "3272c29d-4218-4ec6-903c-1dbfc5c43cce",
	 * "status": 400
	 * "errors": [
        {
            "code": "Code",
            "field": "",
            "error": "Failed to read HTTP message"
        } ]
	 * }
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ServerWebInputException.class )
//OKpublic BadRequestResponse handleJsonParseException(ServerWebInputException ex) {
	public BeanValidationErrorResponse handleJsonParseException(ServerWebInputException ex) {
		
		//400 BAD_REQUEST
		System.err.println("ServerWebInputException: getStatus=" + ex.getStatus());
		
		/*400 BAD_REQUEST "Failed to read HTTP message";
		 * nested exception is org.springframework.core.codec.DecodingException:
		 * JSON decoding error: Unexpected character ('"' (code 34)):
		 * was expecting comma to separate Object entries;
		 * nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character  
		 */
		System.err.println("ServerWebInputException: msg=" + ex.getMessage());
		
		//method 'createOrder' parameter 0
		System.err.println("ServerWebInputException: getMethodParameter=" + ex.getMethodParameter());
		
		//Failed to read HTTP message
		System.err.println("ServerWebInputException: getReason=" + ex.getReason());

		//BadRequestResponse response = new BadRequestResponse(ex.getReason() ); //can be replace with "The info is not correct" or "Bad Request"
							//,this.CODE_JsonParseException);
		
		List<BeanViolationError> errors = Arrays.asList(new BeanViolationError("Code" ,"" ,ex.getReason()));
		
		BeanValidationErrorResponse response = new BeanValidationErrorResponse(errors); 
		this.log(response ,ex);
		return response;
	}

	
	/*NO FUNCIONA
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResponseStatusException.class) //NotFoundException
	//error public String notFound(AbstractMultipartHttpServletRequest request ,Exception ex) {
	//public String notFound(HttpServerRequest request ,Exception ex) { //StandardMultipartHttpServletRequest e
	public String notFound(ResponseStatusException ex) { //StandardMultipartHttpServletRequest e
		System.err.println("ResponseStatusException");
		return "No encontrado = " + ex.getMessage();
	}
	*/
	
	/* Does Not Catch [ServerWebInputException: 400 BAD_REQUEST "Failed to read HTTP message"
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequest.class) //extends WebClientResponseException for status HTTP 400 Bad Request.
	public String badRequest(BadRequest ex) {
		return ex.getMessage();
		
		TODO
		HttpMessageNotReadableException exteds HttpMessageConversionException
	 *  when a conversion attempt fails
	} */
	
	/* NOT_FOUND
	 * " timestamp": "2022-09-06T19:09:02.1814505",
     * " code": "404",
     * " message": "404 Not Found from GET http://localhost:8091/product-api/v1/6317d805295b0843081d465bX",
     * " status": 404
     * 
     * TODO METHOD_NOT_ALLOWED
     * TODO ResponseEntity according to Status
	 */
	@ExceptionHandler(WebClientResponseException.class)
	//OK public NotFoundResponse notFound(WebClientResponseException ex) {
	public ResponseEntity<? extends BaseErrorResponse> handleWebClientResponseException(WebClientResponseException ex) {
		
		// =404 Not Found from GET http://localhost:8091/product-api/v1/6317d805295b0843081d465bx
		System.err.println("WebClientResponseException:  msg=" + ex.getMessage());
		// =404
		System.err.println("WebClientResponseException:  ex.getStatusCode().value() =" + ex.getStatusCode().value());
		// =GET
		System.err.println("WebClientResponseException:  ex.getRequest().getMethodValue() =" + ex.getRequest().getMethodValue() );
		// =http://localhost:8091/product-api/v1/6317d805295b0843081d465bx
		System.err.println("WebClientResponseException:  ex.getRequest().getURI().toString()=" + ex.getRequest().getURI().toString());
		
		System.err.println("WebClientResponseException:  ex.getRequest().getURI().getHost()=" + ex.getRequest().getURI().getHost()); //=localhost
		System.err.println("WebClientResponseException:  ex.getRequest().getURI().getPort()=" + ex.getRequest().getURI().getPort()); //=8091
		// =/product-api/v1/6317d805295b0843081d465bx
		System.err.println("WebClientResponseException:  ex.getRequest().getURI().getPath()=" + ex.getRequest().getURI().getPath());
		System.err.println("WebClientResponseException:  ex.getRequest().getURI().getQuery()=" + ex.getRequest().getURI().getQuery()); //=null
	
		log.warn("WebClientResponseException: Status ={}, msg={}" ,ex.getStatusText() ,ex.getMessage());
		
		BaseErrorResponse response;
		
		if ( HttpStatus.NOT_FOUND.equals( ex.getStatusCode()) ) {
			
			/*Custom message
			 *ex.getMessage() = Descubre toda la Ruta = =404 Not Found from GET http://localhost:8091/product-api/v1/6317d805295b0843081d465bx
			 *getPath() = Solo mostramos el PATH + PathVariable
			 */
			response = new NotFoundResponse(ex.getRequest().getURI().getPath() + " Not Found");	
		}else {
			//regresamos el HttpStatus + Messagge original WebClientResponseException
			response = new BaseErrorResponse( ex.getStatusCode() ,ex.getMessage() );
		}
		 
		log.info(response.toString());
		
		return ResponseEntity.status(ex.getStatusCode()).body(response);  //OK Option 2: return response;
	}

	/* OK DecodingException is Thrown:
	 *   Indicates an issue with decoding the input stream with a focus on content
	 * related issues such as a parse failure
	 *
	 * MismatchedInputException: General exception type used as the base class for all {@link JsonMappingException}
	 *   that are due to input not mapping to target definition;
     *
	 * Caused by: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type
	 *   	When: We Expect Mono<Object> and get Flux<Object> from webClient.get()
	 *   
	 * TO CHANGE this PAYLOAD
	 * { "status": 500,
	 *   "timestamp": "2022-09-08T19:18:34.765+00:00",
     *   "path": "/order-api/v1",
     * 	 "error": "Internal Server Error",
     * 	 "message": "JSON decoding error: Cannot deserialize value of type `mx.com.ismaeloe.webflux_order_service_jpa.dto.ProductDto` from Array value (token `JsonToken.START_ARRAY`); nested exception is com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `mx.com.ismaeloe.webflux_order_service_jpa.dto.ProductDto` from Array value (token `JsonToken.START_ARRAY`)\n at [Source: (io.netty.buffer.ByteBufInputStream); line: 1, column: 1]",
     * 	 "requestId": "f7c30c44-13"
	 * TO
	 * { "status": 500,
     *   "message": "Internal Server Error",
     *   "timestamp": "2022-09-08T15:17:48.148935",
     *   "trace_id": "b4110af3-b56b-46a0-8d86-53638ba1e92e"
     * }
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({DecodingException.class ,MismatchedInputException.class})
	public ServerErrorResponse decodingException(Exception ex) {
		
		ServerErrorResponse response = 
				new ServerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		this.logStackTrace(response ,ex);
		return response; 
	}
	
	/* OK Exception CACHA TODO 
	 * Payload
	 * " timestamp": "2022-09-06T19:09:02.1814505",
     * " message": "404 Not Found from GET http://localhost:8091/product-api/v1/6317d805295b0843081d465bX",
     * " status": 404
	*/
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ServerErrorResponse exception(Exception ex) {
		
		//We do not publish ex.getMessage()
		ServerErrorResponse response =
				new ServerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

		this.logStackTrace(response ,ex);
		return response;
	}

	private void log(BaseErrorResponse response, Exception ex) {

		System.err.println(ex.getMessage());
		//log.warn(response.getTrace_id() , ex); //don't send StackTrace
		log.warn("{}: |{}|{}",ex.getClass().getSimpleName() ,response.getTrace_id(), ex.getMessage());	// real error
		log.info(response.toString());
	}
	
	private void logStackTrace(BaseErrorResponse response, Exception ex) {
		
		System.err.println(ex.getMessage());
		log.error(response.getTrace_id() , ex);	//Send StackTrace
		log.error("{}: |{}|{}",ex.getClass().getSimpleName() ,response.getTrace_id(), ex.getMessage());	// real error
		log.info(response.toString());
	
	}

}
