package net.tigereye.facecavity.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {
    public static float horizontalDistanceTo(Entity entity1,Entity entity2) {
        float f = (float)(entity1.getX() - entity2.getX());
        float h = (float)(entity1.getZ() - entity2.getZ());
        return MathHelper.sqrt(f * f + h * h);
    }

    /**
     * rounds randomly up and down so that it averages out when you do it enough times
     * kinda like it happens in quantum physics
     * @param f the double to round
     * @return the rounded int
     */
    public static int quantum_round(double f){
        int output =(int) f;
        if (ThreadLocalRandom.current().nextDouble()<Math.abs(f-output)){
            output += Math.signum(f);
        }
        return output;
    }

    public static int quantum_round(float f){
        return quantum_round( (double) f);
    }
}
