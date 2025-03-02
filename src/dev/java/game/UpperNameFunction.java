package game;

@FunctionalInterface
public interface UpperNameFunction<T, R> {
    public R apply(T t);
}
