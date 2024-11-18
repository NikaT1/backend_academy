package backend.academy.algo.maze;

import backend.academy.algo.BaseAlgorithm;
import backend.academy.graph.model.Coordinate;
import backend.academy.graph.model.Vertex;
import static backend.academy.random.RandomUtil.getRandomId;

public abstract class BaseGeneratorAlgorithm<T> extends BaseAlgorithm<T> {

    protected Vertex<T> getStartVertex(int width, int height, T initStatus) {
        Vertex<T> vertex = getStartVertex(width, height);
        vertex.status(initStatus);
        return vertex;
    }

    protected Vertex<T> getStartVertex(int width, int height) {
        Coordinate id = getRandomId(width, height);
        return vertices.get(id);
    }
}
