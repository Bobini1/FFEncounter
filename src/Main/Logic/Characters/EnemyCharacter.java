package Main.Logic.Characters;

import Main.Control.Controllable;
import Main.Engine.Actor;
import Main.Engine.Drawing.Renderer;
import Main.Engine.Drawing.Sprites.AnimatedSprite;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Engine.Instance;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.AttackTaker;
import Main.Logic.Components.CharacterState;
import Main.Logic.StatusEffects.StatusEffect;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class EnemyCharacter implements GameCharacter {
    private double energy = 100D;
    private final AnimatedSprite sprite;
    private final CharacterState state;
    private final Instance instance;
    private final List<AttackMethod> methods;
    private AttackMethod nextAction;
    private static final double maxEnergy = 100D;
    private final Random choiceGenerator = new Random();
    private Duration initialWait = Duration.ofSeconds(5);
    private boolean dead = false;

    public EnemyCharacter(CharacterState state, List<AttackMethod> methods, Instance instance, AnimatedSprite sprite) {
        this.sprite = sprite;
        this.instance = instance;
        this.state = state;
        this.methods = methods;
        if (methods.size() != 0) nextAction = methods.get(choiceGenerator.nextInt(methods.size()));
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
        return sprite.getHeight();
    }

    @Override
    public void accept(Renderer renderer) {
        renderer.draw(this);
    }

    @Override
    public void update(Duration dt) {
        if (!initialWait.isNegative())
        {
            initialWait = initialWait.minus(dt);
            sprite.update(dt);
            return;
        }
        energy = Double.min(maxEnergy, energy + dt.getNano() * 5E-9);
        if (energy >= nextAction.getEnergy() && methods.size() != 0)
        {
            List<Actor> targets = instance.getActorsOfType(ControllableGameCharacter.class);
            if (targets.size() == 0)
            {
                initialWait = Duration.ofSeconds(2);
                return;
            }
            AttackTaker target = ((AttackTaker)targets.get(choiceGenerator.nextInt(targets.size())));
            attack(nextAction, target);
            sprite.attack(target);
            energy -= nextAction.getEnergy();
            nextAction = methods.get(choiceGenerator.nextInt(methods.size()));
        }
        for (StatusEffect effect : state.getActiveEffects()) {
            effect.update(dt);
        }
        if (state.getHealth() == 0) {
            instance.remove(this);
            dead = true;
        }
        sprite.update(dt);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public double getMaxEnergy()
    {
        return maxEnergy;
    }

    @Override
    public CharacterState getCharacterState() {
        return state;
    }

    @Override
    public boolean isRemoved()
    {
        return dead;
    }

    @Override
    public Set<StatusEffect> getStatusEffects() {
        return state.getActiveEffects();
    }
}
