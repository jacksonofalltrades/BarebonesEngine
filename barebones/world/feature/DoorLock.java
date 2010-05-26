package barebones.world.feature;

import barebones.engine.GameEngineAccessor;
import barebones.engine.Player;
import barebones.event.ExamineCommand;
import barebones.event.OpenCommand;
import barebones.event.UnlockCommand;
import barebones.event.UserCommand;
import barebones.io.ResponseContent;
import barebones.io.UserResponseImpl;
import barebones.world.data.WODoorLockBean;
import barebones.world.object.Room;
import barebones.world.object.WorldObjectImpl;

public class DoorLock extends WorldObjectImpl implements RoomFeature {
	public static final int SEQ_INDEX = 2;
	
	protected static final String STATE_UNLOCKED = "unlocked";

	protected static final String LOCK_ALREADY_OPEN_TXT = "lock.alreadyopen";
	protected static final String BAD_KEY_TXT = "lock.badkey";
	protected static final String DOOR_LOCKED_TXT = "lock.lockedtxt";
	protected static final String DONT_HAVE_ITEM_TXT = "lock.donthaveitem";
	protected static final String UNLOCK_TXT = "lock.unlocktxt";

	protected WODoorLockBean dldata;
	
	public DoorLock(GameEngineAccessor engineRef, WODoorLockBean data)
	{
		super(engineRef, data);
		this.dldata = data;
	}
	
	public int sequenceIndex() {
		return DoorLock.SEQ_INDEX;
	}
	
	public void filter(UserCommand cmd) {
		// If already unlocked, filter
		if (UnlockCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			if (dldata.state && normTarget.equals(dldata.dir)) {
				String dirText = Room.getDirText(normTarget);
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(LOCK_ALREADY_OPEN_TXT);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));
			}
			else if (!dldata.state && normTarget.equals(dldata.dir)) {
				Player p = m_engineRef.getPlayer();
				UnlockCommand uc = (UnlockCommand)cmd;
				String userItemText = uc.getItemId();
				String userItemId = p.parseItem(userItemText);
				String dirText = Room.getDirText(normTarget);
				
				// Not in inventory
				if (null == userItemId) {
					cmd.disableFor(origTarget);
					
					ResponseContent msg = this.getObjKeyContent(DONT_HAVE_ITEM_TXT);
					msg.formatText(userItemText, dirText);
					cmd.addResponse(new UserResponseImpl(msg));
				}
				// Filter if item is not the right key
				else if (!userItemId.equals(dldata.itemId)) {
					cmd.disableFor(origTarget);
					
					ResponseContent msg = this.getObjKeyContent(BAD_KEY_TXT);
					msg.formatText(userItemText, dirText);
					cmd.addResponse(new UserResponseImpl(msg));
				}
			}
		}
		// if locked, cannot open, so filter
		else if (OpenCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			if (!dldata.state && normTarget.equals(dldata.dir)) {
				String dirText = Room.getDirText(normTarget);
				cmd.disableFor(origTarget);
				
				ResponseContent msg = this.getObjKeyContent(DOOR_LOCKED_TXT);
				msg.formatText(dirText);
				cmd.addResponse(new UserResponseImpl(msg));
			}
		}
	}

	public void process(UserCommand cmd) {
		if (UnlockCommand.class.isInstance(cmd)) {
			String origTarget = cmd.getTarget();
			String normTarget = m_engineRef.parseTarget(cmd);
			if (cmd.isEnabledFor(origTarget) && cmd.isEnabledFor(this.dldata.dir)) {
				if (!dldata.state && normTarget.equals(dldata.dir)) {
					Player p = m_engineRef.getPlayer();
					UnlockCommand uc = (UnlockCommand)cmd;
					String userItemText = uc.getItemId();
					String userItemId = p.parseItem(userItemText);
					if (userItemId.equals(dldata.itemId)) {
						String dirText = Room.getDirText(normTarget);
						dldata.state = true;
						
						this.updateChangedState(cmd);
						
						cmd.setSuccess();
						
						// Replace with custom text if there is any
						ResponseContent unlockTxt = this.getCmdKeyContent(cmd.id());
						if (null == unlockTxt) {
							unlockTxt = this.getObjKeyContent(UNLOCK_TXT);
						}
						unlockTxt.formatText(dirText, userItemText);
						cmd.addResponse(new UserResponseImpl(unlockTxt));
					}
				}
			}
			
		}
		else if (ExamineCommand.class.isInstance(cmd)) {
			
		}
		else {
			ResponseContent cmdText = this.getCmdKeyContent(cmd.id());
			if (null != cmdText) {
				cmd.addResponse(new UserResponseImpl(cmdText));
			}
		}
	}

}
