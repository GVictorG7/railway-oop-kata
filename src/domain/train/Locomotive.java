package domain.train;

public class Locomotive {
    private final LocomotiveType type;

    Locomotive(LocomotiveType type) {
        this.type = type;
    }

    int getPower() {
        switch (type) {
            case MOSQUITO:
                return 200;
            case MAMMOTH:
                return 300;
        }
        return 0;
    }

    @Override
    public String toString() {
        return type + ", power: " + getPower();
    }
}
