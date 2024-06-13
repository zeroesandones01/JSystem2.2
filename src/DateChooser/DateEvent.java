package DateChooser;

import java.util.EventObject;

public class DateEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public DateEvent(DateDispatcher dispatcher) {
		super(dispatcher);
	}

}
