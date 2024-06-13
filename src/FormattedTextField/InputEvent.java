package FormattedTextField;

import java.util.EventObject;

public class InputEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public InputEvent(InputDispatcher dispatcher) {
		super(dispatcher);
	}

}
