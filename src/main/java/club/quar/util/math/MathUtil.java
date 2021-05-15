package club.quar.util.math;

import club.quar.util.type.Tuple;

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

    /**
     * @param data - The set of data you want to find the variance from
     * @return - The variance of the numbers.
     *
     * @See - https://en.wikipedia.org/wiki/Variance
     */
    public static double getVariance(final Collection<? extends Number> data) {
        int count = 0;

        double sum = 0.0;
        double variance = 0.0;

        double average;

        for (Number number : data) {
            sum += number.doubleValue();
            ++count;
        }

        average = sum / count;

        for (Number number : data) {
            variance += Math.pow(number.doubleValue() - average, 2.0);
        }

        return variance;
    }

    /**
     * @param data - The set of numbers / data you want to find the standard deviation from.
     * @return - The standard deviation using the square root of the variance.
     *
     * @See - https://en.wikipedia.org/wiki/Standard_deviation
     * @See - https://en.wikipedia.org/wiki/Variance
     */
    public static double getStandardDeviation(final Collection<? extends Number> data) {
        final double variance = getVariance(data);

        return Math.sqrt(variance);
    }

    /**
     * Converts time in milliseconds to ticks in Minecraft which is equivalent of 50 milliseconds.
     *
     * @param time Time in milliseconds.
     * @return Time in ticks.
     */
    public static int msToTicks(final double time) {
        return (int) Math.round(time / 50.0F);
    }

    /**
     * Returns how much should be added to the current position in order to move on the direction and movement distance given.
     *
     * @param yaw The angle in degrees.
     * @param movement How much movement should be done.
     * @return How much in x and z axis is needed in order to move in the direction given with the given distance.
     */
    public static Tuple<Double, Double> goInDirection(final double yaw, final double movement) {
        final double direction = Math.toRadians(yaw);

        final double moveX = -Math.sin(direction) * movement;
        final double moveZ = Math.cos(direction) * movement;

        return new Tuple<>(moveX, moveZ);
    }
}
