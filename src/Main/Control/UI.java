package Main.Control;

import Main.Engine.Actor;
import Main.Engine.Drawing.Drawable;
import Main.Engine.Instance;
import Main.Engine.Drawing.Renderer;
import Main.Engine.TimeDependent;
import javafx.scene.Scene;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

public class UI implements Drawable, TimeDependent {
    List<Option> options;
    Instance instance;
    UIState state;
    Runnable stateChange = () -> {
    };

    @Override
    public void update(Duration dt) {
        state.update(dt);
        stateChange.run();
        stateChange = () -> {};
    }

    public static class NoChoicesException extends Exception {
        public NoChoicesException(String errorMessage) {
            super(errorMessage);
        }
    }

    public UI(Instance instance, Scene scene) {
        this.instance = instance;

        state = new EmptyUI();

        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> state.select();
                case ESCAPE -> state.goBack();
                case DOWN -> state.cycleChoices(1);
                case UP -> state.cycleChoices(-1);
            }
        });
    }

    private interface UIState extends Drawable, TimeDependent {
        void select();

        void cycleChoices(int howMuch);

        void goBack();
    }

    public static class EmptyUI implements UIState {

        @Override
        public void select() {

        }

        @Override
        public void cycleChoices(int howMuch) {
        }

        @Override
        public void goBack() {

        }

        @Override
        public void accept(Renderer renderer) {

        }

        @Override
        public void update(Duration dt) {

        }
    }

    public class SelectOption implements UIState{
        private final List<Option> options;
        private int highlightedOptionIndex;
        private final UIState previousState;


        public SelectOption(List<Option> options, UIState previousState) {
            this.options = options;
            if (options.isEmpty()) throw new RuntimeException(new NoChoicesException("No options to select from."));
            this.previousState = previousState;
            highlightedOptionIndex = 0;
        }

        public double getHeight() {
            return 200D;
        }

        public List<Option> getOptions() {
            return options;
        }

        public Option getHighlightedOption() {
            return options.get(highlightedOptionIndex);
        }

        @Override
        public void accept(Renderer renderer) {
            renderer.draw(this);
        }

        @Override
        public void select() {
            if (options.get(highlightedOptionIndex).isActive()) options.get(highlightedOptionIndex).execute();
        }

        @Override
        public void cycleChoices(int howMuch) {
            highlightedOptionIndex = (highlightedOptionIndex + 1) % options.size();
        }

        @Override
        public void goBack() {
            state = previousState;
        }

        @Override
        public void update(Duration dt) {

        }
    }

    public class SelectActor implements UIState {
        private final List<Actor> actors;
        private int highlightedActorIndex = 0;
        private final Consumer<Actor> listener;
        private final UIState previousState;

        private SelectActor(Class<?> type, Consumer<Actor> listener, UIState previousState) throws NoChoicesException {
            actors = instance.getActorsOfType(type);
            if (actors.isEmpty()) throw new NoChoicesException("No actors to select from");
            this.listener = listener;
            this.previousState = previousState;
        }

        public SelectActor() throws NoChoicesException {
            actors = instance.getActorsOfType(Controllable.class);
            if (actors.isEmpty()) throw new NoChoicesException("No actors to select from");
            this.listener = actor -> state = new SelectOption(((Controllable) actor).getPrimaryOptions(), this);
            this.previousState = null;
        }

        public void goBack() {
            if (previousState != null) state = previousState;
        }

        public Actor getHighlightedActor() {
            return actors.get(highlightedActorIndex);
        }

        @Override
        public void accept(Renderer renderer) {
            renderer.draw(this);
        }

        @Override
        public void select() {
            listener.accept(actors.get(highlightedActorIndex));
        }

        @Override
        public void cycleChoices(int howMuch) {
            highlightedActorIndex = (highlightedActorIndex + 1) % actors.size();
        }

        @Override
        public void update(Duration dt) {
            if (actors.get(highlightedActorIndex).isRemoved()) cycleChoices(1);
        }
    }

    public void setSelectActorState(Class<?> type, Consumer<Actor> listener) {
        stateChange = () ->
        {
            try {
                state = new SelectActor(type, listener, state);
            } catch (NoChoicesException e) {
                state = new EmptyUI();
            }
        };
    }

    public void setDefaultState() {
        stateChange = () ->
        {
            try {
                state = new SelectActor();
            } catch (NoChoicesException e) {
                state = new EmptyUI();
            }
        };
    }

    public void setSelectOptionState(List<Option> options) {
        stateChange = () -> state = new SelectOption(options, state);
    }

    @Override
    public void accept(Renderer renderer) {
        state.accept(renderer);
    }
}
