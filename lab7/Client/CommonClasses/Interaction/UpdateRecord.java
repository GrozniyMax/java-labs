package CommonClasses.Interaction;

import java.io.Serializable;

public record UpdateRecord(UserAuthData data, Long ID) implements Serializable {
}
