import domain.route.Route;
import domain.route.Station;
import domain.train.Train;
import domain.warehouse.DeliveryPackage;
import domain.warehouse.GoodType;
import domain.warehouse.Warehouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Main {
    private static final List<Train> trains = new ArrayList<>();
    private static final Warehouse warehouse = new Warehouse();
    private static final List<Station> stations = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        int cmd;
        do {
            cmd = readCommand();
            switch (cmd) {
                case 1:
                    first();
                    break;
                case 2:
                    second();
                    break;
                case 3:
                    third();
                    break;
                case 4:
                    forth();
                    break;
            }
        } while (cmd != 0);
    }

    private static int readCommand() {
        int cmd = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            cmd = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println("invalid command");
            e.printStackTrace();
        }
        return cmd;
    }

    private static void first() {
        int numberOfPackages = RANDOM.nextInt(100) + 200;
        System.out.println(numberOfPackages + " packages in total");
        for (int i = 0; i < numberOfPackages; i++) {
            int weight = RANDOM.nextInt(10) + 1;
            int typeIndicator = RANDOM.nextInt(4);

            warehouse.addDeliveryPackage(new DeliveryPackage(getTypeIndicatorFromRandom(typeIndicator), weight));
        }

        System.out.println(warehouse.warehouseSummary());
        warehouse.saveToFile();
    }

    private static GoodType getTypeIndicatorFromRandom(int rand) {
        switch (rand) {
            case 0:
                return GoodType.WHEAT;
            case 1:
                return GoodType.FUEL;
            case 2:
                return GoodType.FURNITURE;
            case 3:
                return GoodType.FRUITS;
        }
        return null;
    }

    private static void second() {
        Route route = new Route();

        for (int i = 0; i < 20; i++) {
            Station station;
            do {
                station = new Station(
                        (RANDOM.nextInt(2) > 0) ? (RANDOM.nextInt(16) + 5) * 20 : 0,
                        (RANDOM.nextInt(2) > 0) ? (RANDOM.nextInt(16) + 5) * 20 : 0,
                        (RANDOM.nextInt(2) > 0) ? (RANDOM.nextInt(16) + 5) * 20 : 0,
                        (RANDOM.nextInt(2) > 0) ? (RANDOM.nextInt(16) + 5) * 20 : 0);
            } while (station.getNumberOfTypes() < 2);

            route.addStation(station);
        }

        route.saveToFile();
    }

    private static void third() {
        trains.add(new Train());
        Path pathWarehouse = Paths.get("warehouse.txt");

        try (Stream<String> linesWarehouse = Files.lines(pathWarehouse)) {
            linesWarehouse.forEach(ln -> {
                String[] s = ln.split(",");
                int weight = Integer.parseInt(s[1].trim().split("wu")[0]);

                DeliveryPackage deliveryPackage = new DeliveryPackage(GoodType.valueOf(s[0]), weight);

                while (!trains.get(trains.size() - 1).addPackage(deliveryPackage)) {
                    trains.add(new Train());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        trains.forEach(System.out::println);
    }

    private static void forth() {
        third();
        readStationsFromFile();

        for (Train train : trains) {
            System.out.println("Train:==========================================================================");
            for (Station station : stations) {
                String result = train.unload(station);

                if (!result.isEmpty()) {
                    System.out.println("Station:");
                    System.out.println(result);
                    System.out.println("Empty wagons: " + train.removeEmptyWagons());
                    System.out.println();
                }
            }
        }
    }

    private static void readStationsFromFile() {
        Path pathRoute = Paths.get("route.txt");
        try (Stream<String> linesRoute = Files.lines(pathRoute)) {
            linesRoute.forEach(ln -> {
                String[] s = ln.split(";");

                Map<GoodType, Integer> content = new HashMap<>();

                for (String str : s) {
                    if (!str.trim().isEmpty()) {
                        String[] ss = str.trim().split("-");
                        content.put(GoodType.valueOf(ss[0]), Integer.parseInt(ss[1].trim().split("wu")[0]));
                    }
                }

                stations.add(new Station(content));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
