package seguros.ti.gestionar.semilla.exceptions;

import com.google.gson.JsonObject;

public class ExceptionResponse {

	private String errorMessage;
	private String requestedURI;
	private JsonObject details;
	
	public ExceptionResponse() {
		this.details = new JsonObject();
	}

	public String getDetails() {
		return details.toString();
	}

	public void setDetails(JsonObject details) {
		this.details = details;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRequestedURI() {
		return requestedURI;
	}

	public void setRequestedURI(final String requestedURI) {
		this.requestedURI = requestedURI;
	}
}
