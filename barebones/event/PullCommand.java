package barebones.event;

import java.util.HashMap;

import barebones.concept.FeatureConcept;

public class PullCommand extends UserCommandImpl 
{
	public static final String id = "PULL";
	public static final String PULL_FEATURE = "feature";
	
	@SuppressWarnings("unchecked")
	public static PullCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(PULL_FEATURE, ArgumentType.FEATURE));
		return new PullCommand(args.get(PULL_FEATURE));
	}
	
	@SuppressWarnings("unchecked")
	public PullCommand(FeatureConcept feature) 
	{		
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(makeSlot(PULL_FEATURE, feature));
		m_target = feature.toString();
	}
	
	public PullCommand(String feature)
	{
		super(TargetScope.CURRENT_ROOM);
		m_target = feature;
	}
	
	public String toString() {
		return "Pull "+m_target;
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}
