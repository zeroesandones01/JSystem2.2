package Functions;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Arrays;
import java.util.List;

public class FncFocusTraversalPolicy extends FocusTraversalPolicy {

	private List<Component> allComponents;
	private int componentCount = 0;

	public FncFocusTraversalPolicy(Component... allComponents) {
		this.allComponents = Arrays.asList(allComponents);
		this.componentCount = this.allComponents.size() -1;
	}

	@Override
	public Component getComponentAfter(Container aContainer, Component aComponent) {
		if(allComponents.indexOf(aComponent) == componentCount){
			return allComponents.get(0);
		}else{
			if(allComponents.get(allComponents.indexOf(aComponent) + 1).isEnabled()){
				return allComponents.get(allComponents.indexOf(aComponent) + 1);
			}else{
				return allComponents.get(allComponents.indexOf(aComponent) + 2);
			}
			
		}
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component aComponent) {
		if(allComponents.indexOf(aComponent) == 0){
			return allComponents.get(componentCount);
		}else{
			try {
				if(allComponents.get(allComponents.indexOf(aComponent) - 1).isEnabled()){
					return allComponents.get(allComponents.indexOf(aComponent) - 1);
				}else{
					return allComponents.get(allComponents.indexOf(aComponent) - 2);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return allComponents.get(0);
			}
			
		}
	}

	@Override
	public Component getFirstComponent(Container aContainer) {
		return allComponents.get(0);
	}

	@Override
	public Component getLastComponent(Container aContainer) {
		return allComponents.get(componentCount);
	}

	@Override
	public Component getDefaultComponent(Container aContainer) {
		return allComponents.get(0);
	}

}
