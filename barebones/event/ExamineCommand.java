package barebones.event;

import java.util.HashMap;

import barebones.concept.AmbiguousConcept;
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
		if (null == target)
			m_target = "";
		else
			m_target = target;
	}

	@SuppressWarnings("unchecked")
	public ExamineCommand(AmbiguousConcept ambConcept)
	{
		addSlotConcepts(makeSlot(EXAM_TARGET, ambConcept));
		m_target = ambConcept.toString();
	}
	
	@SuppressWarnings("unchecked")
	public ExamineCommand(FeatureConcept fConcept)
	{
		addSlotConcepts(makeSlot(EXAM_TARGET, fConcept));
		m_target = fConcept.toString();
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
