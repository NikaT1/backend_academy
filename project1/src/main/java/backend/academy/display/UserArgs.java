package backend.academy.display;

import backend.academy.words.model.ComplexityLevel;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserArgs {
    @Parameter(names = {"-c", "-category"}, description = "Category of words")
    private String category;
    private ComplexityLevel level;

    @Parameter(names = {"-l", "-level"}, description = "Level of complexity")
    public void setLevel(String value) {
        this.level = ComplexityLevel.valueOf(value);
    }

}
