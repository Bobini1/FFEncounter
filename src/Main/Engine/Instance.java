package Main.Engine;

import Main.Control.UI;
import Main.Engine.Drawing.JFXRenderer;
import Main.Engine.Drawing.Renderer;
import Main.Logic.PlayerActors.Actor;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.time.Duration;

import java.util.ArrayList;
import java.util.List;

public class Instance extends javafx.application.Application {
    List<Actor> activeActors;

    private Stage stage;
    private GraphicsContext gc;
    private Background bg;

    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("FFEncounter");
        stage.setResizable(false);

        Group root = new Group();

        Scene theScene = new Scene(root, 800, 800);
        stage.setScene(theScene);

        Canvas canvas = new Canvas( 800, 800 );
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        stage.show();

        bg = new Background(new Image(String.valueOf(getClass().getResource("/BG.png"))));
        UI ui = new UI(this, canvas);
        loop();
    }

    public void loop()
    {
        new AnimationTimer()
        {
            long previousFrame = System.nanoTime();

            @Override public void handle(long currentNanoTime)
            {
                Duration dt = Duration.ofNanos(currentNanoTime - previousFrame);
                previousFrame = currentNanoTime;
                for (Actor actor : activeActors)
                {
                    actor.update(dt);
                }
                Renderer renderer = new JFXRenderer(gc);
                bg.accept(renderer);
                for (Actor actor : activeActors)
                {
                    actor.accept(renderer);
                }
                renderer.renderAll();
            }
        }.start();
    }

    public List<Actor> getActorsOfType(Class<?> type)
    {
        List<Actor> result = new ArrayList<Actor>();
        for (Actor actor : activeActors)
        {
            if (type.isInstance(actor)) result.add(actor);
        }
        return result;
    }

    public void instantiate(Actor actor)
    {
        activeActors.add(actor);
    }

    public void remove(Actor actor)
    {
        activeActors.remove(actor);
    }

    public Stage getStage()
    {
        return stage;
    }

    public GraphicsContext getGC()
    {
        return gc;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

