package predicates;

import planners.IStackItem;

public interface IPredicate extends IStackItem{
	
	public boolean isSatisfied(IPredicate predicate);
	
}
