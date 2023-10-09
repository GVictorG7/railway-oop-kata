package domain.route;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Route {
    private final List<Station> stations;

    public Route() {
        stations = new ArrayList<>();
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    private String routeSummary() {
        StringBuilder result = new StringBuilder();
        for (Station station : stations) {
            System.out.println(station.toString());
            result.append(station).append("\n");
        }
        return result.toString();
    }

    public void saveToFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("route.txt"))) {
            out.write(routeSummary());
            out.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
