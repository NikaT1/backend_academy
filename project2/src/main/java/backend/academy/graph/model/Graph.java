package backend.academy.graph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<Coordinate, List<Coordinate>> edges = new HashMap<>();

    public void addCoordinate(Coordinate id) {
        edges.putIfAbsent(id, new ArrayList<>());
    }

    public Set<Coordinate> getCoordinates() {
        return edges.keySet();
    }

    public void addEdge(Coordinate c1, Coordinate c2) {
        edges.get(c1).add(c2);
        edges.get(c2).add(c1);
    }

    public boolean hasEdge(Coordinate c1, Coordinate c2) {
        if (!edges.containsKey(c1)) {
            return false;
        }
        return edges.get(c1).contains(c2);
    }
}
