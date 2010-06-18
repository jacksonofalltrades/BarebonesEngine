package barebones.world.feature;

import barebones.engine.GameEngineAccessor;
import barebones.event.ExamineCommand;
import barebones.event.MoveCommand;
import barebones.event.OpenCommand;
import barebones.event.UnlockCommand;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.UserResponseImpl;
import barebones.world.data.CommandTrigger;
import barebones.world.data.WOSecretDoorBean;
import barebones.world.object.Room;
import barebones.world.object.WorldObjectImpl;

public class SecretDoor extends WorldObjectImpl implements RoomFeature {
	public static final int SEQ_INDEX = 0;
	
	/*
	protected static final String STATE_OPEN = "open";
	protected static final String DOOR_CLOSED_TXT = "door.closedtxt";
	protected static final String DOOR_DESC_TXT = "door.desctxt";
	protected static final String OPEN_DOOR_TXT = "door.opentxt";
	protected static final String DOOR_ALREADY_OPEN_TXT = "door.alreadyopen";
	protected static final String OPEN_TXT = "door.openadj";
	protected static final String CLOSED_TXT = "door.closedadj";
*/
	protected static final String NOTHING_TO_OPEN = "secretdoor.nothingtoopen";
	protected static final String NOTHING_TO_UNLOCK = "secretdoor.nothingtounlock";
	protected static final String INVALID_DIR_TXT = "secretdoor.invaliddirtxt";
	
	protected WOSecretDoorBean sdata;
	
	public SecretDoor(GameEngineAccessor engineRef, WOSecretDoorBean data) {
		super(engineRef, data);
		sdata = data;
	}
	
	public int sequenceIndex() {
		return SecretDoor.SEQ_INDEX;
	}

	public void filter(UserCommand cmd) {
		String origTarget = cmd.getTarget();
		String normTarget = m_engineRef.parseTarget(cmd);

		if (OpenCommand.class.isInstance(cmd)) {
			if (!sdata.state && normTarget.equals(sdata.dir)) {
				String dirText = Room.getDirText(normTarget);
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(NOTHING_TO_OPEN);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));
			}
		}
		else if (UnlockCommand.class.isInstance(cmd)) {
			if (!sdata.state && normTarget.equals(sdata.dir)) {
				String dirText = Room.getDirText(normTarget);
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(NOTHING_TO_UNLOCK);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));				
			}
		}
		else if (MoveCommand.class.isInstance(cmd)) {
			if (!sdata.state && normTarget.equals(sdata.dir)) {
				String dirText = Room.getDirText(normTarget);
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(INVALID_DIR_TXT);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));				
			}
		}
		else if (ExamineCommand.class.isInstance(cmd)) {
			if (!sdata.state) {
				cmd.disableFor(sdata.dir);
			}
		}
	}

	public void process(UserCommand cmd) {
		//String origTarget = cmd.getTarget();
		String normTarget = m_engineRef.parseTarget(cmd);
		
		boolean triggered = false;
		if (null != this.sdata.triggerList) {
			for(CommandTrigger trigger : this.sdata.triggerList) {
				boolean cmdMatches = trigger.getcommandId().equals(cmd.id());
				boolean targetMatches = trigger.gettarget().equals(normTarget);
				if (cmdMatches && targetMatches) {
					this.sdata.state = true;
					this.updateChangedState(cmd);
					
					cmd.setSuccess();
					
					triggered = true;
					
					ResponseContent text = this.getTriggerContent();
					cmd.addResponse(text);
					break;					
				}
			}
		}
		
		boolean relevant = (normTarget.equals(this.sdata.dir) || normTarget.equals(ExamineCommand.DEFAULT_TARGET));
		if (ExamineCommand.class.isInstance(cmd) && relevant) {
			if (!this.sdata.state && !triggered) {
				// Pre-trigger examine
				ResponseContent text = this.getPreTriggerContent();
				cmd.addResponse(text);
			}
			else if (this.sdata.state && !triggered){
				// Post-trigger examine
				ResponseContent text = this.getPostTriggerContent();
				cmd.addResponse(text);
			}
		}
			
		if (!triggered && !ExamineCommand.class.isInstance(cmd)) {
			ResponseContent cmdText = this.getCmdKeyContent(cmd.id());
			if (null != cmdText) {
				cmd.addResponse(new UserResponseImpl(cmdText));
			}
		}
	}
}
