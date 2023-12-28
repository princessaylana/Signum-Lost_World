package za.lana.signum.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum WizardEntityVariant {
    DEFAULT(0),
    BLACK(1),
    BLUE (2),
    CYAN (3),
    PINK(4),
    PURPLE(5),
    RED(6),
    WHITE(7),
    YELLOW(8);

    private static final WizardEntityVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(WizardEntityVariant::getId)).toArray(WizardEntityVariant[]::new);
    private final int id;

    WizardEntityVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static WizardEntityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
