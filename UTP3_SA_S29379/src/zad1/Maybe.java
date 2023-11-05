package zad1;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Maybe<T> {
    private T value; // the value of the Maybe object
    private boolean isPresent; // a flag indicating whether the value is present or not

    private Maybe(T value) { // a private constructor
        this.value = value;
        this.isPresent = value != null;
    }

    public static <T> Maybe<T> of(T value) { // a static factory method
        return new Maybe<>(value);
    }

    public void ifPresent(Consumer<? super T> cons) { // a method to execute a consumer function
        if (isPresent) {
            cons.accept(value);
        }
    }

    public <U> Maybe<U> map(Function<? super T, ? extends U> func) { // a method to transform the value
        if (isPresent) {
            return new Maybe<>(func.apply(value));
        } else {
            return new Maybe<>(null);
        }
    }

    public T get() { // a method to get the value
        if (isPresent) {
            return value;
        } else {
            throw new NoSuchElementException("maybe is empty");
        }
    }

    public T orElse(T defVal) { // a method to get the value or a default value
        if (isPresent) {
            return value;
        } else {
            return defVal;
        }
    }

    public Maybe<T> filter(Predicate<? super T> pred) { // a method to filter the value
        if (isPresent && pred.test(value)) {
            return this;
        } else {
            return new Maybe<>(null);
        }
    }

    @Override
    public String toString() { // a method to print the Maybe object
        if (isPresent) {
            return "Maybe has value " + value;
        } else {
            return "Maybe is empty";
        }
    }
}
