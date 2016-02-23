package io.ologn.common.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Java has BiFunction, so why not TriFunction?
 * @author lisq199
 *
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <R>
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {
	
	public R apply(T t, U u, V v);
	
	public default <W> TriFunction<T, U, V, W> andThen(
			Function<? super R, ? extends W> after) {
		Objects.requireNonNull(after);
		return (T t, U u, V v) -> after.apply(apply(t, u, v));
	}
	
}
