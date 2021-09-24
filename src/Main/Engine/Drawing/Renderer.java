package Main.Engine.Drawing;

import Main.Control.UI;
import Main.Engine.Background;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Characters.GameCharacter;

public interface Renderer {
    void draw(Sprite sprite);
    void draw(Background bg);
    void draw(UI ui);
    void renderAll();

    void draw(UI.SelectOption selectOption);
    void draw(UI.SelectActor selectActor);
    void draw(GameCharacter character);
}
