package Lookup;

public interface ResetDispatcher {

	public void addResetListener(ResetListener listener);

	public void doReset();
}
