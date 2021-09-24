package Main.Logic.Components;

import Main.Logic.Actions.ActionCommand;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.List;
import java.util.Set;

public interface AttackTaker {
    CharacterState getCharacterState();
    Set<StatusEffect> getStatusEffects();

    default void takeAttack(ActionCommand action)
    {
        for (StatusEffect statusEffect : getStatusEffects())
        {
            action = statusEffect.processIncomingAction(action);
        }
        getCharacterState().giveAction(action);
    }
}
