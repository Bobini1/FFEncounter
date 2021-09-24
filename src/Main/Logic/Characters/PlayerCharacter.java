package Main.Logic.Characters;

import Main.Control.Controllable;
import Main.Control.Option;
import Main.Control.UI;
import Main.Engine.Drawing.Renderer;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Components.*;
import Main.Logic.StatusEffects.Defending;
import Main.Logic.StatusEffects.StatusEffect;
import javafx.scene.image.Image;

import java.time.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerCharacter implements GameCharacter, Controllable {

    private final UI controllingUI;
    private final List<Option> primaryOptions = new ArrayList<>();
    private final AttackingManager attackingManager;
    private double energy = 100D;
    private Sprite sprite;
    private CharacterState state;

    public PlayerCharacter(CharacterState state, List<AttackMethod> methods, UI controllingUI, Sprite sprite)
    {
        this.state = state;
        this.sprite = sprite;
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
                state.addStatusEffect(new Defending(Duration.ofSeconds(20), state.getActiveEffects()));
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

    @Override
    public Sprite getSprite()
    {
        return sprite;
    }

    public List<Option> getPrimaryOptions()
    {
        return primaryOptions;
    }

    @Override
    public double getPositionX() {
        return sprite.getPosition().getX();
    }

    @Override
    public double getPositionY() {
        return sprite.getPosition().getY();
    }

    @Override
    public double getWidth() {
        return sprite.getWidth();
    }

    @Override
    public double getHeight() {
        return sprite.getWidth();
    }

    private class AttackingManager
    {
        List<Option> attackMethodOptions = new ArrayList<>();
        AttackMethod selectedAttackMethod;
        public AttackingManager(List<AttackMethod> methods)
        {
            for (AttackMethod method : methods)
            {
                attackMethodOptions.add(new Option() {

                    @Override
                    public void execute() {
                        selectedAttackMethod = method;
                        controllingUI.setSelectActorState(AttackTaker.class, actor ->
                            setTargetAndLaunch((AttackTaker)actor)
                        );
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
        for (StatusEffect effect : state.getActiveEffects())
        {
            effect.update(dt);
        }
    }

    @Override
    public CharacterState getCharacterState() {
        return state;
    }

    @Override
    public Set<StatusEffect> getStatusEffects() {
        return state.getActiveEffects();
    }
}
