package Main.Logic.Components;

import Main.Logic.Actions.ActionCommand;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.List;

public interface AttackDealer {
    CharacterState getCharacterState();
    List<StatusEffect> getStatusEffects();

    default void attack(AttackMethod method, AttackTaker target)
    {
        ActionCommand action = new ActionCommand(getCharacterState().getStrength() * method.getDamage(),
                                                method.getTrueDamage(), method.getEffects());
        for (StatusEffect effect : getStatusEffects())
        {
            action = effect.processOutgoingAction(action);
        }
        target.takeAttack(action);
    }
}
