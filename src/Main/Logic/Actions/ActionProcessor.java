package Main.Logic.Actions;

public interface ActionProcessor {
    public Main.Logic.Actions.ActionCommand processIncomingAction(Main.Logic.Actions.ActionCommand action);
    public Main.Logic.Actions.ActionCommand processOutgoingAction(Main.Logic.Actions.ActionCommand action);
}
