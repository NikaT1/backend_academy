package backend.academy.graph.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Vertex<T> {
    @Setter
    private T status;
    private final Coordinate id;
}
