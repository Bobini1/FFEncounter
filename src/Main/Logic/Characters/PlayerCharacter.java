package Main.Logic.Characters;

import Main.Control.Controllable;
import Main.Control.Option;
import Main.Control.UI;
import Main.Engine.Drawing.Renderer;
import Main.Logic.Components.*;
import Main.Logic.StatusEffects.Defending;
import Main.Logic.StatusEffects.StatusEffect;
import java.time.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerCharacter implements GameCharacter, Controllable {

    private final UI controllingUI;
    private final List<Option> primaryOptions = new ArrayList<Option>();
    private final AttackingManager attackingManager;
    private final Set<StatusEffect> activeEffects = new HashSet<StatusEffect>();
    private double energy = 100D;

    public PlayerCharacter(CharacterState state, List<AttackMethod> methods, UI controllingUI, Position)
    {
        this.controllingUI = controllingUI;
        attackingManager = new AttackingManager(methods);
        primaryOptions.add(new Option() {

            @Override
            public void execute() {
                controllingUI.setSelectOptionState(attackingManager.getAttackMethodOptions());
            }

            @Override
            public Boolean isActive() {
                return true;
            }

            @Override
            public String getName() {
                return "Attack";
            }
        });
        primaryOptions.add(new Option() {

            @Override
            public void execute() {
                activeEffects.add(new Defending(Duration.ofSeconds(20), activeEffects));
                controllingUI.setDefaultState();
            }

            @Override
            public Boolean isActive() {
                return true;
            }

            @Override
            public String getName() {
                return "Defend";
            }
        });
    }

    public List<Option> getPrimaryOptions()
    {
        return primaryOptions;
    }

    @Override
    public double getPositionX() {
        return 0;
    }

    @Override
    public double getPositionY() {
        return 0;
    }

    private class AttackingManager
    {
        List<Option> attackMethodOptions = new ArrayList<Option>();
        AttackMethod selectedAttackMethod;
        public AttackingManager(List<AttackMethod> methods)
        {
            for (AttackMethod method : methods)
            {
                attackMethodOptions.add(new Option() {

                    @Override
                    public void execute() {
                        selectedAttackMethod = method;
                        controllingUI.setSelectActorState(AttackTaker.class, actor -> {
                            setTargetAndLaunch((AttackTaker)actor);
                        });
                    }

                    @Override
                    public Boolean isActive() {
                        return method.getEnergy() <= energy;
                    }

                    @Override
                    public String getName() {
                        return method.getName();
                    }
                });
            }
        }

        public List<Option> getAttackMethodOptions()
        {
            return attackMethodOptions;
        }

        public void setTargetAndLaunch(AttackTaker target)
        {
            attack(selectedAttackMethod, target);
            controllingUI.setDefaultState();
        }
    }

    @Override
    public void accept(Renderer renderer) {
        renderer.draw(this);
    }

    @Override
    public void update(Duration dt) {
        energy = Double.min(100D, energy + dt.getNano() * 1E-10);
        for (StatusEffect effect : activeEffects)
        {
            effect.update(dt);
        }
    }

    @Override
    public CharacterState getCharacterState() {
        return null;
    }

    @Override
    public List<StatusEffect> getStatusEffects() {
        return null;
    }
}