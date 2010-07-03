package barebones.concept;

import java.lang.reflect.Method;
import java.util.HashMap;

import barebones.engine.GameEngineAccessor;
import barebones.event.UserCommand.TargetScope;

public final class ConceptResolver 
{
	// Should be moved to a config or tied more tightly to ConceptContainer
	protected static HashMap<Class<? extends Concept>,Method> sm_conceptResolverMethodMap;
	
	protected static void addCRM(Class<? extends Concept> concept, Method m)
	{
		sm_conceptResolverMethodMap.put(concept, m);
	}
	
	static {
		sm_conceptResolverMethodMap = new HashMap<Class<? extends Concept>,Method>();
		try {
			addCRM(FeatureConcept.class, ConceptContainer.class.getMethod("getFeaturePatternIdMap"));
			addCRM(PersonConcept.class, ConceptContainer.class.getMethod("getPersonPatternIdMap"));
			addCRM(ItemConcept.class, ConceptContainer.class.getMethod("getItemPatternIdMap"));
		}
		catch(NoSuchMethodException nsme) {
			System.err.println(nsme.getMessage());
		}
	}
	
	private ConceptResolver() {}

	/**
	 * How concepts are resolved:
	 * 1. Use scope passed in to determine starting container
	 * 2. Fetch reference to container from engineRef.
	 * 3. Loop through map of target keys to concepts
	 * 4. For each target concept, use concept class type to retrieve the resolver method.
	 * 5. Call resolver method to get map of patterns to ids
	 * 6. Attempt to match pattern string in Concept object to a pattern in pattern-map. 
	 *    If found, set associated id in id map.
	 * 7. Return id map
	 * @param engineRef
	 * @param scope
	 * @param commandTargetConceptMap
	 * @return map of command targets to concept ids after resolving
	 * 
	 */
	public static HashMap<String,String>
	resolveTargetConcepts(GameEngineAccessor engineRef, TargetScope scope, HashMap<String,Concept> commandTargetConceptMap)
	{
		// TODO: Fill this in
		//foreach()
		return null;
	}
}
