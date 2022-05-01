package counter.withModel;

import javafx.beans.property.SimpleIntegerProperty;

public class Counter extends SimpleIntegerProperty {
    public void incrementByOne() {
        this.set(this.add(1).get());
    }
}
