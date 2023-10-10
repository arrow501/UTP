/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad1;


@FunctionalInterface
public interface Mapper<T, R> {
    R map(T input);
}
