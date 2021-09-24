package Main.Logic.StatusEffects;

import Main.Logic.Actions.ActionProcessor;
import Main.Engine.TimeDependent;

public interface StatusEffect extends TimeDependent, ActionProcessor {
    double getProgress();
}
