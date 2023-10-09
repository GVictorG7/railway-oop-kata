package domain.route;

import domain.warehouse.GoodType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Station {
    private final Map<GoodType, Integer> contentWeight;

    public Station(int wheatWeight, int fuelWeight, int furnitureWeight, int fruitsWeight) {
        contentWeight = new HashMap<>();

        if (wheatWeight > 0) {
            contentWeight.put(GoodType.WHEAT, wheatWeight);
        }
        if (fuelWeight > 0) {
            contentWeight.put(GoodType.FUEL, fuelWeight);
        }
        if (furnitureWeight > 0) {
            contentWeight.put(GoodType.FURNITURE, furnitureWeight);
        }
        if (fruitsWeight > 0) {
            contentWeight.put(GoodType.FRUITS, fruitsWeight);
        }
    }

    public Station(Map<GoodType, Integer> contentWeight) {
        this.contentWeight = contentWeight;
    }

    public Set<GoodType> getContentTypes() {
        return contentWeight.keySet();
    }

    public int getNumberOfTypes() {
        return contentWeight.size();
    }

    public int getContentWeightByType(GoodType type) {
        if (contentWeight.containsKey(type)) {
            return contentWeight.get(type);
        }
        return 0;
    }

    public void setContentWeightByType(GoodType type, int value) {
        if (contentWeight.containsKey(type)) {
            contentWeight.put(type, value);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (GoodType type : contentWeight.keySet()) {
            result.append(type).append("-").append(contentWeight.get(type)).append("wu; ");
        }

        return result.toString();
    }
}
