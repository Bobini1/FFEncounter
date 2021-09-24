package Main.Engine;

import Main.Control.UI;
import Main.Engine.Drawing.JFXRenderer;
import Main.Engine.Drawing.Renderer;
import Main.Engine.Drawing.Sprites.SimpleSprite;
import Main.Engine.Drawing.Sprites.Sprite;
import Main.Logic.Characters.PlayerCharacterBuilder;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;
import Main.Logic.Components.Position;
import Main.Logic.StatusEffects.StatusEffect;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.time.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Instance extends javafx.application.Application {
    List<Actor> activeActors = new ArrayList<>();

    private GraphicsContext gc;
    private Background bg;
    private UI ui;

    @Override
    public void start(Stage stage){
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
        ui = new UI(this, theScene);


        PlayerCharacterBuilder builder = new PlayerCharacterBuilder(ui);
        builder.setSprite(new SimpleSprite(new Image(String.valueOf(getClass().getResource("/Warrior.png"))),
                new Position(100, 100), 10, 1.5));
        builder.setAttackMethods(List.of(new AttackMethod(5, 1,
                new ArrayList<>(), 50, "Sword")));
        builder.setState(new CharacterState(100F, 2F, 1F));

        instantiate(builder.getResult());

        builder.setSprite(new SimpleSprite(new Image(String.valueOf(getClass().getResource("/Warrior.png"))),
                new Position(200, 100), 10, 1.5));
        instantiate(builder.getResult());

        ui.setDefaultState();

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
                ui.accept(renderer);
                renderer.renderAll();
            }
        }.start();
    }

    public List<Actor> getActorsOfType(Class<?> type)
    {
        List<Actor> result = new ArrayList<>();
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

    public static void main(String[] args)
    {
        launch(args);
    }
}

