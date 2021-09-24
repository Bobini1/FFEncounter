package Main.Engine;

import Main.Engine.Drawing.Drawable;
import Main.Engine.Drawing.Renderer;
import javafx.scene.image.Image;

public class Background implements Drawable {
    private final Image bgImage;

    public Background(Image image)
    {
        this.bgImage = image;
    }

    public Image getImage() {
        return bgImage;
    }

    @Override
    public void accept(Renderer renderer) {
        renderer.draw(this);
    }
}
