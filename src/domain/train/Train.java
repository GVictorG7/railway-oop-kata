package domain.train;

import javafx.util.Pair;
import domain.route.Station;
import domain.warehouse.DeliveryPackage;
import domain.warehouse.GoodType;

import java.util.*;

public class Train {
    private final List<Locomotive> locomotives;
    private final List<Wagon> wagons;

    public Train() {
        locomotives = new ArrayList<>();
        locomotives.add(new Locomotive(LocomotiveType.MAMMOTH));

        wagons = new ArrayList<>();
        for (WagonType type : WagonType.values()) {
            wagons.add(new Wagon(type));
        }
    }

    private int getTotalWeight() {
        return wagons.stream().mapToInt(Wagon::getTotalWeight).sum();
    }

    private int getTotalPower() {
        return locomotives.stream().mapToInt(Locomotive::getPower).sum();
    }

    private boolean addWagon(Wagon wagon) {
        if (wagons.size() < 20) {
            wagons.add(wagon);
            return true;
        }

        return false;
    }

    public int removeEmptyWagons() {
        int result = 0;
        int i = 0;

        while (i < wagons.size()) {
            Wagon wagon = wagons.get(i);

            if (wagon.isEmpty()) {
                wagons.remove(wagon);
                i--;
                result++;
            }
            i++;
        }

        return result;
    }

    public boolean addPackage(DeliveryPackage deliveryPackage) {
        for (Wagon wagon : wagons) {
            if (wagon.addPackage(deliveryPackage)) {
                if (getTotalWeight() > getTotalPower()) {
                    locomotives.add(new Locomotive(LocomotiveType.MAMMOTH));
                }
                return true;
            }
        }
        if (addWagon(new Wagon(WagonType.LARGE))) {
            return addPackage(deliveryPackage);
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Train:\n - Locomotives:\n");
        for (Locomotive locomotive : locomotives) {
            result.append(locomotive.toString()).append("\n");
        }

        result.append(" - Wagons:\n");
        for (Wagon wagon : wagons) {
            result.append(wagon.toString()).append("\n");
        }

        return result.toString();
    }

    public String unload(Station station) {
        Map<GoodType, Integer> initialValues = new HashMap<>();
        Map<GoodType, Pair<Integer, Integer>> unloadResult = new HashMap<>();
        Set<GoodType> stationTypes = station.getContentTypes();

        for (GoodType type : stationTypes) {
            unloadResult.put(type, new Pair<>(0, 0));
            initialValues.put(type, station.getContentWeightByType(type));
        }

        for (Wagon wagon : wagons) {
            int i = 0;
            // iterating the packages in the wagon
            while (i < wagon.getPackages().size()) {
                DeliveryPackage deliveryPackage = wagon.getPackages().get(i);

                for (GoodType type : stationTypes) {
                    if (deliveryPackage.getGoodType() == type & deliveryPackage.getWeight() <= station.getContentWeightByType(type)) {
                        unloadResult.put(type,
                                new Pair<>(unloadResult.get(type).getKey() + 1,
                                        unloadResult.get(type).getValue() + deliveryPackage.getWeight()));
                        station.setContentWeightByType(type, station.getContentWeightByType(type) - deliveryPackage.getWeight());
                        wagon.removePackage(deliveryPackage);
                        i--;
                    }
                }

                i++;
            }
        }

        StringBuilder result = new StringBuilder();
        for (GoodType type : stationTypes) {
            result.append(type).append(" - Total: ").append(initialValues.get(type)).append("wu, Received: ").append(unloadResult.get(type).getValue()).append("wu, in ").append(unloadResult.get(type).getKey()).append(" delivery packages\n");
        }

        return result.toString();
    }
}
