package Main.Engine.Drawing;

import Main.Control.Option;
import Main.Control.UI;
import Main.Engine.Background;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Characters.GameCharacter;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class JFXRenderer implements Renderer{
    private final List<Sprite> toRender;
    private final GraphicsContext context;

    private static final Comparator<Sprite> zIndexOrdering = new Comparator<Sprite>() {
        @Override
        public int compare(Sprite first, Sprite second)
        {
            return Integer.compare(first.getZ(), second.getZ());
        }
    };

    public JFXRenderer(GraphicsContext gc)
    {
        context = gc;
        toRender = new LinkedList<Sprite>();
    }

    @Override
    public void draw(Sprite sprite) {
        toRender.add(sprite);
    }
    @Override
    public void draw(Background bg)
    {
        context.drawImage(bg.getImage(), 0,0, context.getCanvas().getWidth(), context.getCanvas().getHeight());

    }

    @Override
    public void draw(UI ui) {
        context.fillRect(context.getCanvas().getWidth(), context.getCanvas().getHeight(), );
    }

    public void renderAll()
    {
        toRender.sort(zIndexOrdering);
        for (Sprite sprite : toRender)
        {
            context.drawImage(sprite.getImage(),
                    sprite.getX(), sprite.getY(),
                    sprite.getWidth(), sprite.getHeight());
        }
    }

    @Override
    public void draw(UI.SelectOption selectOption) {
        double canvasHeight = context.getCanvas().getHeight();
        double canvasWidth = context.getCanvas().getWidth();
        double boxHeight = selectOption.getHeight();

        context.fillRect(0, canvasHeight - boxHeight, canvasWidth, boxHeight);
        List<Option> options = selectOption.getOptions();
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        for (int i = 0; i < options.size(); i++)
        {
            context.fillText(options.get(i).getName(), canvasWidth / 2,
                    canvasHeight - boxHeight + boxHeight * ((float)i / options.size()));
        }

    }

    @Override
    public void draw(UI.SelectActor selectActor) {
        double positionX = selectActor.getHighlightedActor().getPositionX();
        double positionY = selectActor.getHighlightedActor().getPositionY();
        context.setTextAlign(TextAlignment.CENTER);
        context.setTextBaseline(VPos.CENTER);
        context.fillText("selected", positionX, positionY);
    }

    @Override
    public void draw(GameCharacter character) {

    }
}
