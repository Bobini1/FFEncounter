package Main.Logic.Components;

import Main.Logic.Actions.ActionCommand;
import Main.Logic.StatusEffects.StatusEffect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface AttackDealer {
    CharacterState getCharacterState();
    Set<StatusEffect> getStatusEffects();

    default void attack(AttackMethod method, AttackTaker target)
    {
        ActionCommand action = new ActionCommand(getCharacterState().getStrength() * method.getDamage(),
                                                method.getTrueDamage(), new HashSet<>(method.getEffects()));
        for (StatusEffect effect : getStatusEffects())
        {
            action = effect.processOutgoingAction(action);
        }
        target.takeAttack(action);
    }
}
