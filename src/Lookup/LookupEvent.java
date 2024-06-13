package Lookup;

import java.util.EventObject;

public class LookupEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public LookupEvent(LookupDispatcher dispatcher) {
		super(dispatcher);
	}

}
