package club.quar.util.type;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A triple can store 3 completely separate objects at once
 *
 * @param <X> Type of the first variable.
 * @param <Y> Type of the second variable.
 * @param <Z> Type of the third variable.
 */
@Data
@AllArgsConstructor
public final class Triple<X, Y, Z> {
    private X first;
    private Y second;
    private Z third;
}
