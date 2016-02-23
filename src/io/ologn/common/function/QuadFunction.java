package io.ologn.common.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Since I now have TriFunction, why not QuadFunction? 
 * @author lisq199
 *
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <W>
 * @param <R>
 */
@FunctionalInterface
public interface QuadFunction<T, U, V, W, R> {

	public R apply(T t, U u, V v, W w);
	
	public default <X> QuadFunction<T, U, V, W, X> andThen(
			Function<? super R, ? extends X> after) {
		Objects.requireNonNull(after);
		return (T t, U u, V v, W w) -> after.apply(apply(t, u, v, w));
	}
	
}
