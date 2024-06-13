package search;

import java.util.EventObject;

public class SearchEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	public SearchEvent(SearchDispatcher dispatcher) {
		super(dispatcher);
	}

}
