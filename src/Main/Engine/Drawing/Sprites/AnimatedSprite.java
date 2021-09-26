package Main.Engine.Drawing.Sprites;

import Main.Engine.Actor;
import Main.Logic.Components.AttackTaker;
import Main.Logic.Components.Position;
import javafx.scene.image.Image;

import java.time.Duration;

public class AnimatedSprite implements TimeDependentSprite {
    private final Image defaultImage;
    private final int z;
    private final Position position;
    private final double scale;
    private final Image attackingImage;
    private TimeDependentSprite state;

    public AnimatedSprite(Image defaultImage, Image attackingImage, Position position, int z, double scale) {
        this.defaultImage = defaultImage;
        this.attackingImage = attackingImage;
        this.position = position;
        this.z = z;
        this.scale = scale;
        state = new Default();
    }

    private class Default implements TimeDependentSprite {
        @Override
        public Image getImage() {
            return defaultImage;
        }

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public int getZ() {
            return z;
        }

        @Override
        public double getWidth() {
            return defaultImage.getWidth() * scale;
        }

        @Override
        public double getHeight() {
            return defaultImage.getHeight() * scale;
        }

        @Override
        public SimpleSprite clone() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void update(Duration dt) {
        }
    }

    private class Attacking implements TimeDependentSprite {
        private Duration accumulator = Duration.ZERO;
        private boolean reachedTarget = false;
        private final Actor target;
        private Position position = AnimatedSprite.this.position;

        private Attacking(Actor target) {
            this.target = target;
        }

        @Override
        public Image getImage() {
            return attackingImage;
        }

        @Override
        public Position getPosition() {
            return position;
        }

        @Override
        public int getZ() {
            return z + 1;
        }

        @Override
        public double getWidth() {
            return attackingImage.getWidth() * scale;
        }

        @Override
        public double getHeight() {
            return attackingImage.getHeight() * scale;
        }

        @Override
        public SimpleSprite clone() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void update(Duration dt) {
            accumulator = accumulator.plus(dt);
            if (!reachedTarget) {
                if (accumulator.compareTo(Duration.ofMillis(250)) <= 0) {
                    position = new Position(lerp(position.getX(), target.getPositionX(),
                            accumulator.getNano() * 4E-9D),
                            lerp(position.getY(), target.getPositionY(),
                                    accumulator.getNano() * 4E-9D));
                } else {
                    reachedTarget = true;
                    accumulator = Duration.ZERO;
                }
            } else {
                if (accumulator.compareTo(Duration.ofMillis(250)) <= 0) {
                    position = new Position(lerp(target.getPositionX(), AnimatedSprite.this.position.getX(),
                            accumulator.getNano() * 4E-9D),
                            lerp(target.getPositionY(), AnimatedSprite.this.position.getY(),
                                    accumulator.getNano() * 4E-9D));
                } else {
                    state = new Default();
                }
            }
        }
    }

    static double lerp(double a, double b, double amount) {
        return (a * (1.0 - amount)) + (b * amount);
    }

    public AnimatedSprite(Image defaultImage, Image attackingImage, Position position, int z) {
        this(defaultImage, attackingImage, position, z, 1D);
    }

    @Override
    public Image getImage() {
        return state.getImage();
    }

    @Override
    public Position getPosition() {
        return state.getPosition();
    }

    public Position getDefaultPosition()
    {
        return position;
    }

    @Override
    public int getZ() {
        return state.getZ();
    }

    @Override
    public double getWidth() {
        return state.getWidth() * scale;
    }

    @Override
    public double getHeight() {
        return state.getHeight() * scale;
    }

    @Override
    public AnimatedSprite clone() {
        return new AnimatedSprite(defaultImage, attackingImage, position.clone(), z, scale);
    }

    @Override
    public void update(Duration dt) {
        state.update(dt);
    }

    public void attack(Actor target) {
        state = new Attacking(target);
    }
}
