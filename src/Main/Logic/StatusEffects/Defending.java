package Main.Logic.StatusEffects;

import Main.Logic.Actions.ActionCommand;
import java.time.Duration;

import java.util.Set;

public class Defending implements StatusEffect {
    private final Set<StatusEffect> activeEffects;
    private final Duration duration;
    private Duration accumulator;

    public Defending(Duration duration, Set<StatusEffect> activeEffects)
    {
        this.activeEffects = activeEffects;
        this.duration = duration;
        this.accumulator = Duration.ZERO;
    }

    @Override
    public void update(Duration dt) {
        accumulator = accumulator.plus(dt);
        if (duration.compareTo(accumulator) < 0) activeEffects.remove(this);
    }

    @Override
    public double getProgress()
    {
        return (double)duration.minus(accumulator).toNanos() / duration.toNanos();
    }

    @Override
    public StatusEffect clone() {
        return new Defending(duration, activeEffects);
    }

    @Override
    public ActionCommand processIncomingAction(ActionCommand action) {
        return new ActionCommand(action.getDamage() > 0 ? action.getDamage() * 0.8F : action.getDamage(),
                action.getTrueDamage(), action.getEffects());
    }

    @Override
    public ActionCommand processOutgoingAction(ActionCommand action) {
        return action;
    }
}
