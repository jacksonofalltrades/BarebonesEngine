package barebones.world.feature;

import barebones.engine.GameEngineAccessor;
import barebones.event.ExamineCommand;
import barebones.event.MoveCommand;
import barebones.event.OpenCommand;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.UserResponseImpl;
import barebones.world.data.WODoorBean;
import barebones.world.object.Room;
import barebones.world.object.WorldObjectImpl;

public class Door extends WorldObjectImpl implements RoomFeature {
	public static final int SEQ_INDEX = 1;
	
	protected static final String STATE_OPEN = "open";
	protected static final String DOOR_CLOSED_TXT = "door.closedtxt";
	protected static final String DOOR_DESC_TXT = "door.desctxt";
	protected static final String OPEN_DOOR_TXT = "door.opentxt";
	protected static final String DOOR_ALREADY_OPEN_TXT = "door.alreadyopen";
	protected static final String OPEN_TXT = "door.openadj";
	protected static final String CLOSED_TXT = "door.closedadj";

	protected WODoorBean ddata;
	
	public Door(GameEngineAccessor engineRef, WODoorBean data) {
		super(engineRef, data);
		ddata = data;
	}
	
	public int sequenceIndex() {
		return Door.SEQ_INDEX;
	}

	public void filter(UserCommand cmd) {
		// Ignore if the direction of this door is not enabled
		if (!cmd.isEnabledFor(this.ddata.dir)) {
			return;
		}		
		
		if (MoveCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			String dirText = Room.getDirText(normTarget);
			if ((!ddata.state) && normTarget.equals(ddata.dir)) {
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(DOOR_CLOSED_TXT);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));
			}
		}
		else if (OpenCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			if (ddata.state && normTarget.equals(ddata.dir)) {
				String dirText = Room.getDirText(normTarget);
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(DOOR_ALREADY_OPEN_TXT);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));
			}
		}
	}

	public void process(UserCommand cmd) {
		if (ExamineCommand.class.isInstance(cmd)) {
			
			// Ignore if the direction of this door is not enabled
			if (!cmd.isEnabledFor(this.ddata.dir)) {
				return;
			}
			
			ResponseContent openTxt = this.getObjKeyContent(OPEN_TXT);
			ResponseContent closedTxt = this.getObjKeyContent(CLOSED_TXT);
			
			String normTarget = ddata.dir;
			String dirText = Room.getDirText(normTarget);
			String stateTxt = (ddata.state?openTxt.text():closedTxt.text());
			
			// Replace with custom text if there is any
			ResponseContent doorDescTxt = this.getCmdKeyContent(cmd.id());
			if (null == doorDescTxt) {
				doorDescTxt = this.getObjKeyContent(DOOR_DESC_TXT);				
			}
			doorDescTxt.formatText(stateTxt, dirText);
			cmd.addResponse(new UserResponseImpl(doorDescTxt));
		}
		else if (OpenCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			if (cmd.isEnabledFor(origTarget)) {
				if (!ddata.state && normTarget.equals(ddata.dir)) {
					String dirText = Room.getDirText(normTarget);
					ddata.state = true;
					
					this.updateChangedState(cmd);
					
					cmd.setSuccess();
					
					// Replace with custom text if there is any
					ResponseContent openDoorTxt = this.getCmdKeyContent(cmd.id());
					if (null == openDoorTxt) {
						openDoorTxt = this.getObjKeyContent(OPEN_DOOR_TXT);
					}
					openDoorTxt.formatText(dirText);
					cmd.addResponse(new UserResponseImpl(openDoorTxt));
				}
			}
		}
		else {
			ResponseContent cmdText = this.getCmdKeyContent(cmd.id());
			if (null != cmdText) {
				cmd.addResponse(new UserResponseImpl(cmdText));
			}
		}
	}
}
