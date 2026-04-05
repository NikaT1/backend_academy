package backend.academy.image.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    private short colorR;
    private short colorG;
    private short colorB;
    private int hitCount = 0;

    public void incHitCount() {
        hitCount++;
    }
}
