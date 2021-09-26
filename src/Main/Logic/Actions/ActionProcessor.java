package Main.Logic.Actions;

public interface ActionProcessor {
    Main.Logic.Actions.ActionCommand processIncomingAction(Main.Logic.Actions.ActionCommand action);

    Main.Logic.Actions.ActionCommand processOutgoingAction(Main.Logic.Actions.ActionCommand action);
}
