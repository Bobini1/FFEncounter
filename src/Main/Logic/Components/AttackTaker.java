package Main.Logic.Components;

import Main.Logic.Actions.ActionCommand;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.List;

public interface AttackTaker {
    CharacterState getCharacterState();
    List<StatusEffect> getStatusEffects();

    default void takeAttack(ActionCommand action)
    {
        for (StatusEffect statusEffect : getStatusEffects())
        {
            action = statusEffect.processIncomingAction(action);
        }
        getCharacterState().giveAction(action);
    }
}
