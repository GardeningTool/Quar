package club.quar.util.type;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A tuple can store 2 completely separate objects at once.
 *
 * @param <X> Type of the first variable.
 * @param <Y> Type of the second variable.
 */
@Data
@AllArgsConstructor
public final class Tuple<X, Y> {
    private X first;
    private Y second;
}