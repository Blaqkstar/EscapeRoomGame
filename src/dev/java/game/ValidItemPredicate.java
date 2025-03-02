package game;

@FunctionalInterface
public interface ValidItemPredicate<T> {
    public boolean test(T t);
}
