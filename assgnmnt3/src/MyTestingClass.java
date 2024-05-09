import java.util.Objects;
import java.util.Random;

public class MyTestingClass {
    private int value;

    public MyTestingClass(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        // Custom hashCode method to ensure uniform distribution
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }
}


