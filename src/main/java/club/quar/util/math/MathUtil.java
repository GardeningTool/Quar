package club.quar.util.math;

import java.util.Collection;

/**
 * MathUtil is a class where basic Math operations are implemented in fast matter.
 *
 * This class will grow with contributors help and the developers ideas.
 */
public final class MathUtil {

    /**
     * Make sure nobody can't instantiate the class.
     */
    private MathUtil() {
        throw new RuntimeException("MathUtil class is a utility class and should not be instantiated.");
    }

    /**
     * Get the average of the numbers given as a collection.
     *
     * @param data The collection with the numbers.
     * @return The average of the values.
     */
    public static double getAverage(Collection<? extends Number> data) {
        double sum = 0.0D;

        for (Number number : data) {
            sum += number.doubleValue();
        }

        return sum / data.size();
    }

    /**
     * Clamps the number given by the limits.
     *
     * @param val The number to be clamped.
     * @param min The minimum value.
     * @param max The maximum value.
     * @return The clamped number.
     */
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Sums up squared version of every number given and gets the square root.
     *
     * @param data The collection with the numbers.
     * @return Sums up squared version of every number given and gets the square root.
     */
    public static double hypot(double... data) {
        double total = 0.0D;

        for (Number number : data) {
            total += number.doubleValue() * number.doubleValue();
        }

        return Math.sqrt(total);
    }

    /**
     * Sums up squared version of every number given and gets the square root.
     *
     * @param data The collection with the numbers.
     * @return Sums up squared version of every number given and gets the square root.
     */
    public static double hypot(Collection<? extends Number> data) {
        double total = 0.0D;

        for (Number number : data) {
            total += number.doubleValue() * number.doubleValue();
        }

        return Math.sqrt(total);
    }
}
