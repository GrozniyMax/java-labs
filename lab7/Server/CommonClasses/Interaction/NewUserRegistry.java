package CommonClasses.Interaction;

import java.io.Serializable;

public record NewUserRegistry(UserAuthData authData) implements Serializable {
}
