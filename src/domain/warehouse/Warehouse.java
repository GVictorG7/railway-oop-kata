package domain.warehouse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<DeliveryPackage> packages;

    public Warehouse() {
        packages = new ArrayList<>();
    }

    public void addDeliveryPackage(DeliveryPackage deliveryPackage) {
        packages.add(deliveryPackage);
    }

    public String warehouseSummary() {
        StringBuilder result = new StringBuilder();
        int totalPackages;
        int totalWeight;
        double totalVolume;

        for (GoodType goodType : GoodType.values()) {
            totalPackages = 0;
            totalWeight = 0;
            totalVolume = 0;
            for (DeliveryPackage deliveryPackage : packages) {
                if (deliveryPackage.getGoodType().equals(goodType)) {
                    totalPackages++;
                    totalWeight += deliveryPackage.getWeight();
                    totalVolume += deliveryPackage.getVolume();
                }
            }

            System.out.println(goodType + ": Total packages: " + totalPackages + ", Total Weight: " + totalWeight
                    + "wu, Total Volume: " + totalVolume + "vu;");
            result.append(goodType).append(": Total packages: ").append(totalPackages).append(", Total Weight: ")
                    .append(totalWeight).append("wu, Total Volume: ").append(totalVolume).append("vu;\n");
        }

        return result.toString();
    }

    private String warehouseContent() {
        StringBuilder result = new StringBuilder();
        for (DeliveryPackage deliveryPackage : packages) {
            result.append(deliveryPackage.toString()).append("\n");
        }
        System.out.println(result);

        return result.toString();
    }

    public void saveToFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("warehouse.txt"))) {
            out.write(warehouseContent());
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
