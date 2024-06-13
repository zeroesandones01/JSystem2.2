package Lookup;

import java.util.EventObject;

public class ResetEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public ResetEvent(ResetDispatcher dispatcher) {
		super(dispatcher);
	}

}
