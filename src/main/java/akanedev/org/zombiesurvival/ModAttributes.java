package akanedev.org.zombiesurvival;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;

public class ModAttributes {
    public static final Identifier POWER_ID = new Identifier(ZombieSurvival.modID, "power");
    private static final double DEFAULT_POWER_VALUE = 0.0;
    private static final double MIN_POWER_VALUE = -1000.0;
    private static final double MAX_POWER_VALUE = Double.MAX_VALUE;

    public static final EntityAttribute POWER = new ClampedEntityAttribute(
            "zombiesurvival.power", // translation key
            DEFAULT_POWER_VALUE,    // fallback
            MIN_POWER_VALUE,        // min
            MAX_POWER_VALUE        // max
    ).setTracked(true);
}
