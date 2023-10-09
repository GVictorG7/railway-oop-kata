package domain.train;

import domain.warehouse.DeliveryPackage;
import domain.warehouse.GoodType;

import java.util.ArrayList;
import java.util.List;

public class Wagon {
    private final WagonType type;
    private final List<DeliveryPackage> packages;

    Wagon(WagonType type) {
        this.type = type;
        this.packages = new ArrayList<>();
    }

    List<DeliveryPackage> getPackages() {
        return packages;
    }

    private int getCapacity() {
        switch (type) {
            case SMALL:
                return 50;
            case MEDIUM:
                return 75;
            case LARGE:
                return 100;
        }

        return 0;
    }

    int getTotalWeight() {
        return packages.stream().mapToInt(DeliveryPackage::getWeight).sum();
    }

    boolean isEmpty() {
        return packages.isEmpty();
    }

    void removePackage(DeliveryPackage deliveryPackage) {
        packages.remove(deliveryPackage);
    }

    private double getUsedVolume() {
        return packages.stream().mapToDouble(DeliveryPackage::getVolume).sum();
    }

    boolean addPackage(DeliveryPackage deliveryPackage) {
        if (deliveryPackage.getVolume() <= getCapacity() - getUsedVolume()) {
            packages.add(deliveryPackage);
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(type + ": Total volume: " + getCapacity() + "vu, Total weight: "
                + getTotalWeight() + ", Used Volume: " + getUsedVolume() + "\n");
        int totalWeight;
        double totalVolume;

        for (GoodType goodType : GoodType.values()) {
            totalWeight = 0;
            totalVolume = 0;

            for (DeliveryPackage deliveryPackage : packages) {
                if (deliveryPackage.getGoodType().equals(goodType)) {
                    totalWeight += deliveryPackage.getWeight();
                    totalVolume += deliveryPackage.getVolume();
                }
            }

            if (totalWeight != 0) {
                result.append(goodType).append(": ").append(totalWeight).append("wu = ").append(totalVolume).append("vu\n");
            }
        }

        return result.toString();
    }
}
