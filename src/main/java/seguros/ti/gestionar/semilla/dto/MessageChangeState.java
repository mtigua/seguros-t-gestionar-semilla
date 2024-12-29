package seguros.ti.gestionar.semilla.dto;
import lombok.Data;
@Data
public class MessageChangeState {
	private Long id;
	private String message;
	private boolean state;
    
	public MessageChangeState() {}
	public MessageChangeState(Long id,boolean state,String message) {
		super();
		this.id = id;
		this.state = state;
		this.message=message;
	}
}