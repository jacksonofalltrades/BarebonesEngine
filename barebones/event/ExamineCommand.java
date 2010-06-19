package barebones.event;

import java.util.HashMap;

import barebones.concept.AmbiguousConcept;
import barebones.concept.DirectionConcept;
import barebones.concept.FeatureConcept;

public class ExamineCommand extends UserCommandImpl {
	public static final String id = "EXAM";
	
	public static final String EXAM_TARGET = "target";	
	public static final String DEFAULT_TARGET = "room";
	
	public static ExamineCommand instance(HashMap<String,String> args) {
		return new ExamineCommand(args.get(EXAM_TARGET));
	}
	
	public ExamineCommand() {
		this(new FeatureConcept(DEFAULT_TARGET));
	}
	
	public ExamineCommand(String target) {
		super(TargetScope.CURRENT_ROOM);
		if (null == target)
			m_target = "";
		else
			m_target = target;
	}

	@SuppressWarnings("unchecked")
	public ExamineCommand(AmbiguousConcept ambConcept)
	{
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(makeSlot(EXAM_TARGET, ambConcept));
		m_target = ambConcept.toString();
	}
	
	@SuppressWarnings("unchecked")
	public ExamineCommand(FeatureConcept fConcept)
	{
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(makeSlot(EXAM_TARGET, fConcept));
		m_target = fConcept.toString();
	}

	@SuppressWarnings("unchecked")
	public ExamineCommand(DirectionConcept dConcept)
	{
		super(TargetScope.CURRENT_ROOM);
		addSlotConcepts(makeSlot(EXAM_TARGET, dConcept));
		m_target = dConcept.toString();
	}	
	
	public boolean causesTick() {
		return false;
	}

	public String id() {
		return id;
	}
	
	public String toString() {
		return "Examine"+(null!=m_target?" "+m_target:"");
	}
}
