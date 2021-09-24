package Main.Engine.Drawing;

import Main.Control.Option;
import Main.Control.UI;
import Main.Engine.Background;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Characters.GameCharacter;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class JFXRenderer implements Renderer{
    private final List<Sprite> toRender;
    private final GraphicsContext context;
    Runnable drawUI;
    List<Runnable> lifeBars = new ArrayList<>();

    private static final Comparator<Sprite> zIndexOrdering = Comparator.comparingInt(Sprite::getZ);

    public JFXRenderer(GraphicsContext gc)
    {
        context = gc;
        toRender = new LinkedList<>();
    }

    @Override
    public void draw(Background bg)
    {
        context.drawImage(bg.getImage(), 0,0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
    }

    public void renderAll()
    {
        toRender.sort(zIndexOrdering);
        for (Sprite sprite : toRender)
        {
            context.drawImage(sprite.getImage(),
                    sprite.getPosition().getX(), sprite.getPosition().getY(),
                    sprite.getWidth(), sprite.getHeight());
        }
        if (drawUI != null) drawUI.run();
        for (Runnable drawLifeBar : lifeBars)
        {
            drawLifeBar.run();
        }
    }

    @Override
    public void draw(UI.SelectOption selectOption) {
        drawUI = () -> {
            double canvasHeight = context.getCanvas().getHeight();
            double canvasWidth = context.getCanvas().getWidth();
            double boxHeight = selectOption.getHeight();

            context.setFill(Color.BLUE);
            context.fillRect(0, canvasHeight - boxHeight, canvasWidth, boxHeight);
            List<Option> options = selectOption.getOptions();
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            for (int i = 0; i < options.size(); i++) {
                if (!options.get(i).isActive()) context.setFill(Color.GRAY);
                else if (options.get(i) == selectOption.getHighlightedOption()) context.setFill(Color.RED);
                else context.setFill(Color.GREEN);
                context.fillText(options.get(i).getName(), canvasWidth / 2,
                        canvasHeight - boxHeight + boxHeight * ((float) i / options.size()));
            }
        };
    }

    @Override
    public void draw(UI.SelectActor selectActor) {
        drawUI = () -> {
            double positionX = selectActor.getHighlightedActor().getPositionX();
            double positionY = selectActor.getHighlightedActor().getPositionY();
            context.setTextAlign(TextAlignment.CENTER);
            context.setTextBaseline(VPos.CENTER);
            context.setFill(Color.RED);
            context.fillText("selected", positionX, positionY);
        };
    }

    @Override
    public void draw(GameCharacter character) {
        toRender.add(character.getSprite());
        lifeBars.add(() -> {
                    context.setFill(Color.DARKSLATEGRAY);
                    context.fillRect(character.getSprite().getPosition().getX() - 20,
                            character.getSprite().getPosition().getY() - 4, 40, 8);
                    context.setFill(Color.GREEN);
                    context.fillRect(character.getSprite().getPosition().getX() - 20,
                            character.getSprite().getPosition().getY() - 4,
                            (character.getCharacterState().getHealth() / character.getCharacterState().getInitialHealth()) * 40, 8);
                });
    }
}
