package Main.Logic.Characters.Builders;

import Main.Control.UI;
import Main.Engine.Drawing.Sprites.AnimatedSprite;
import Main.Engine.Instance;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;
import Main.Logic.Components.Position;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class GenericCharacterDirector {

    private final Image mainImage = new Image(String.valueOf(getClass().getResource("/Warrior.png")));

    public void constructAtPosition(GameCharacterBuilder builder, Position position)
    {
        builder.setSprite(new AnimatedSprite(mainImage, mainImage,
                position, 10, 1.5));
        builder.setAttackMethods(List.of(new AttackMethod(25, 1,
                new ArrayList<>(), 50, "Sword")));
        builder.setState(new CharacterState(100F, 2F, 1F));
    }
}
