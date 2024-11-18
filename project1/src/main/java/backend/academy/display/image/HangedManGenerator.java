package backend.academy.display.image;

import static backend.academy.display.image.HangedManPartsImages.BOTTOM;
import static backend.academy.display.image.HangedManPartsImages.HUMAN_STEPS;
import static backend.academy.display.image.HangedManPartsImages.HUMAN_STEPS_DIFF;
import static backend.academy.display.image.HangedManPartsImages.MIDDLE_WITH_HUMAN;
import static backend.academy.display.image.HangedManPartsImages.MIDDLE_WITH_ROPE;
import static backend.academy.display.image.HangedManPartsImages.SIMPLE_MIDDLE;
import static backend.academy.display.image.HangedManPartsImages.TOP;

public class HangedManGenerator implements PictureGenerator {

    private int currentStep = 0;
    private final int maxStep;
    private final int middleImageStep;
    private final String[] hangedManImage;

    public HangedManGenerator(int maxStep) {
        this.maxStep = maxStep;
        middleImageStep = averageRoundedDownStep(maxStep);
        hangedManImage = new String[middleImageStep + 1];
    }

    private int averageRoundedDownStep(int maxStep) {
        return (maxStep - HUMAN_STEPS_DIFF + 1) / 2 + 1;
    }

    @Override
    public void updatePicture() {
        currentStep++;

        if (currentStep == 1) {
            hangedManImage[currentStep - 1] = BOTTOM;
        } else if (currentStep < middleImageStep) {
            hangedManImage[currentStep - 1] = SIMPLE_MIDDLE;
        } else if (currentStep <= middleImageStep) {
            hangedManImage[currentStep - 1] = TOP;
        } else if (currentStep <= maxStep - HUMAN_STEPS) {
            addMiddleWithRopePart();
        } else {
            addHumanPart();
        }
    }

    @Override
    public String getPicture() {
        StringBuilder result = new StringBuilder();

        for (int i = middleImageStep - 1; i >= 0; i--) {
            if (hangedManImage[i] != null) {
                result.append(hangedManImage[i]);
            }
        }

        return result.toString();
    }

    private void addMiddleWithRopePart() {
        int indexToReplace = 2 * middleImageStep - currentStep - 1;
        hangedManImage[indexToReplace] = MIDDLE_WITH_ROPE;
    }

    private void addHumanPart() {
        int partIndex = HUMAN_STEPS - (maxStep - currentStep) - 1;
        if (partIndex < HUMAN_STEPS_DIFF) {
            hangedManImage[2 + maxStep % 2] = MIDDLE_WITH_HUMAN[partIndex];
        } else {
            hangedManImage[1 + maxStep % 2] = MIDDLE_WITH_HUMAN[partIndex];
        }
    }

}
