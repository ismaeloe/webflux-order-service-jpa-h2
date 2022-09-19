package mx.com.ismaeloe.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BeanViolationError {

	private String code;
	private String field;
	private String error;
}
