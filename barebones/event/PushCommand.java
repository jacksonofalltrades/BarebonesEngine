package barebones.event;

import java.util.HashMap;

import barebones.concept.FeatureConcept;

public class PushCommand extends UserCommandImpl 
{
	public static final String id = "PUSH";
	public static final String PUSH_FEATURE = "feature";
	
	@SuppressWarnings("unchecked")
	public static PushCommand instance(HashMap<String,String> args) {
		checkMissingArgs(id, args, arg(PUSH_FEATURE, ArgumentType.FEATURE));
		return new PushCommand(args.get(PUSH_FEATURE));
	}
	
	@SuppressWarnings("unchecked")
	public PushCommand(FeatureConcept feature)
	{
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(makeSlot(PUSH_FEATURE, feature));
		m_target = feature.toString();
	}
	
	public PushCommand(String feature) 
	{		
		super(TargetScope.CURRENT_ROOM);
		m_target = feature;
	}
	
	public String toString() {
		return "Push "+m_target;
	}

	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}

}
