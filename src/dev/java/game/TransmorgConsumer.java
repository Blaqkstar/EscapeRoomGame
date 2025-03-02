package game;

@FunctionalInterface
public interface TransmorgConsumer<T> {
    public void accept(T t);
}