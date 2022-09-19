package mx.com.ismaeloe.exceptions;

import java.time.ZoneId;
//import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;

//@AllArgsConstructor //No crea el constructor en el orden que requiero
//@SuppressWarnings("serial")
//@Data
@Getter
public class BaseErrorResponse { //extends RuntimeException {
	
	//@Getter(value=AccessLevel.NONE)
	  @Getter(value=AccessLevel.PRIVATE)
	private HttpStatus httpStatus;

	//private Integer status;
	public int getStatus() {
		return this.httpStatus.value();
	}

	private String message;
	/*ok Pero Sin Zona Horararia "timestamp": "2022-09-12T02:38:50.3004752"
	 * private LocalDateTime timestamp;	
	 */
	//Con Zona Horaria -5;00  "timestamp": "2022-09-12T02:37:37.848168-05:00" 
	private ZonedDateTime timestamp;
	private String trace_id;

	public BaseErrorResponse(HttpStatus httpStatus, String message) {
		
		//this.status = status;
		this.httpStatus = httpStatus;
		this.message = message;
		//this.timestamp = LocalDateTime.now();
		this.timestamp = ZonedDateTime.now(ZoneId.of("GMT-5"));

		this.trace_id = UUID.randomUUID().toString();
	}
	
	@Override
	public String toString() {
		
		/* this.getClass().getName() = mx.com.ismaeloe.exceptions.ServerErrorResponse
		 * this.getClass().getSimpleName() = ServerErrorResponse
		 */
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName())
				.append(": |trace_id=").append(trace_id)
				.append("|status=").append(this.getStatus()) //status
				.append(", message=").append(message);
				//.append(", timestamp=").append(timestamp)

		return builder.toString();
	}

}
