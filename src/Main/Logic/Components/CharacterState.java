package Main.Logic.Components;

import Main.Logic.Actions.ActionCommand;

public class CharacterState {
    float initialHealth;
    float health;
    float defense;
    float strength;

    public CharacterState(float health, float defense, float strength)
    {
        this.health = health;
        this.initialHealth = health;
        this.defense = defense;
        this.strength = strength;
    }


    public float getHealth()
    {
        return health;
    }

    public float getDefense()
    {
        return defense;
    }

    public float getStrength()
    {
        return strength;
    }

    public void giveAction(ActionCommand action) {
        float damage = action.getDamage();
        if (action.getDamage() < 0)
        {
            damage = Float.max(action.getDamage() - defense, 0f);
        }

        health = health - damage - action.getTrueDamage();
        if (health > initialHealth)
        {
            health = initialHealth;
        }
    }
}
