package domain.warehouse;

public class DeliveryPackage {
    private final GoodType goodType;
    private final int weight;

    public DeliveryPackage(GoodType goodType, int weight) {
        this.goodType = goodType;
        this.weight = weight;
    }

    public GoodType getGoodType() {
        return goodType;
    }

    public int getWeight() {
        return weight;
    }

    private double getVolumeFactor() {
        switch (goodType) {
            case WHEAT:
                return (float) 1.2;
            case FUEL:
                return 1;
            case FURNITURE:
                return 8;
            case FRUITS:
                return 4;
        }

        return 0;
    }

    public double getVolume() {
        return getVolumeFactor() * weight;
    }

    @Override
    public String toString() {
        return goodType + ", " +
                weight + "wu, " +
                weight + "x" + getVolumeFactor() + " = " +
                getVolume() + "vu;";
    }
}
