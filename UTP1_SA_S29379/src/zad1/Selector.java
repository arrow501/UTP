/**
 *
 *  @author Święch Aleksander S29379
 *
 */

package zad1;


@FunctionalInterface
public interface Selector<T> {
    public boolean select(T input);
}
