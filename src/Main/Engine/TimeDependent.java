package Main.Engine;

import java.time.Duration;

public interface TimeDependent {
    void update(Duration dt);
}
