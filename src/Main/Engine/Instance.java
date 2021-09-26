package Main.Engine;

import Main.Control.UI;
import Main.Engine.Drawing.JFXRenderer;
import Main.Engine.Drawing.Renderer;
import Main.Engine.Drawing.Sprites.AnimatedSprite;
import Main.Logic.Characters.Builders.EnemyCharacterBuilder;
import Main.Logic.Characters.Builders.GenericCharacterDirector;
import Main.Logic.Characters.Builders.PlayerCharacterBuilder;
import Main.Logic.Components.AttackMethod;
import Main.Logic.Components.CharacterState;
import Main.Logic.Components.Position;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.Duration;

import java.util.*;

public class Instance extends javafx.application.Application {
    List<Actor> activeActors = new ArrayList<>();

    private GraphicsContext gc;
    private Background bg;
    private UI ui;
    Queue<Actor> actorsToRemove = new LinkedList<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("FFEncounter");
        stage.setResizable(false);

        Group root = new Group();

        Scene theScene = new Scene(root, 800, 800);
        stage.setScene(theScene);

        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        stage.show();

        bg = new Background(new Image(String.valueOf(getClass().getResource("/BG.png"))));
        ui = new UI(this, theScene);

        GenericCharacterDirector director = new GenericCharacterDirector();
        PlayerCharacterBuilder playerBuilder = new PlayerCharacterBuilder(ui, this);

        director.constructAtPosition(playerBuilder, new Position(100, 100));
        instantiate(playerBuilder.getResult());

        director.constructAtPosition(playerBuilder, new Position(400, 100));
        instantiate(playerBuilder.getResult());

        EnemyCharacterBuilder enemyBuilder = new EnemyCharacterBuilder(this);
        director.constructAtPosition(enemyBuilder, new Position(300, 300));

        instantiate(enemyBuilder.getResult());

        ui.setDefaultState();

        loop();
    }

    public void loop() {
        new AnimationTimer() {
            long previousFrame = System.nanoTime();

            @Override
            public void handle(long currentNanoTime) {
                Duration dt = Duration.ofNanos(currentNanoTime - previousFrame);
                previousFrame = currentNanoTime;
                Iterator<Actor> actorIterator = activeActors.iterator();
                while (actorIterator.hasNext()) {
                    Actor actor = actorIterator.next();
                    actor.update(dt);
                }
                activeActors.removeAll(actorsToRemove);
                actorsToRemove.clear();

                ui.update(dt);

                Renderer renderer = new JFXRenderer(gc);
                bg.accept(renderer);
                for (Actor actor : activeActors) {
                    actor.accept(renderer);
                }
                ui.accept(renderer);
                renderer.renderAll();
            }
        }.start();
    }

    public List<Actor> getActorsOfType(Class<?> type) {
        List<Actor> result = new ArrayList<>();
        for (Actor actor : activeActors) {
            if (type.isInstance(actor)) result.add(actor);
        }
        return result;
    }

    public boolean isNoActiveActors() {
        return activeActors.isEmpty();
    }

    public void instantiate(Actor actor) {
        activeActors.add(actor);
    }

    public void remove(Actor actor) {
        actorsToRemove.add(actor);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

