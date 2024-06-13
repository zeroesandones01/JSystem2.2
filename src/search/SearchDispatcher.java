package search;

public interface SearchDispatcher {

	public void setRETURN_DATA(Object[] RETURN_DATA);

	public Object[] getRETURN_DATA();

	public void addSearchListener(SearchListener listener);

	public void doSearch();

	public void dispatchEvent();

	public void dispatchRunnableOnEventQueue(SearchListener listener, SearchEvent event);

}
