package io.ologn.common.math;

import java.util.function.Function;

import io.ologn.common.OlognHashCode;

/**
 * Inspired by d3.scale.linear(). Implemented mainly for choosing colors. 
 * The default domain and range are both [0, 1]. The methods are pretty 
 * self-explanatory.<br>
 * Note: the lower bound of the domain/range does not have to be smaller 
 * than the upper bound.<br>
 * Typical usage: 
 * {@code LinearScale.init().setDomain(10, 20).setRange(0, 5).apply(x);}
 * @author lisq199
 *
 */
public class LinearScale {
	
	protected double domainMin;
	protected double domainMax;
	protected double rangeMin;
	protected double rangeMax;
	
	/**
	 * Hidden constructor. Sets domain and range to default values.
	 */
	protected LinearScale() {
		this.domainMin = 0;
		this.domainMax = 1;
		this.rangeMin = 0;
		this.rangeMax = 1;
	}
	
	/**
	 * Get the lower bound of the domain
	 * @return
	 */
	public double getDomainMin() {
		return domainMin;
	}
	
	/**
	 * Get the upper bound of the domain
	 * @return
	 */
	public double getDomainMax() {
		return domainMax;
	}
	
	/**
	 * Set the domain
	 * @param min lower bound
	 * @param max upper bound
	 * @return
	 */
	public LinearScale setDomain(double min, double max) {
		this.domainMin = min;
		this.domainMax = max;
		return this;
	}
	
	/**
	 * Get the lower bound of the range
	 * @return
	 */
	public double getRangeMin() {
		return rangeMin;
	}
	
	/**
	 * Get the upper bound of the range
	 * @return
	 */
	public double getRangeMax() {
		return rangeMax;
	}
	
	/**
	 * Set the range
	 * @param min lower bound
	 * @param max upper bound
	 * @return
	 */
	public LinearScale setRange(double min, double max) {
		this.rangeMin = min;
		this.rangeMax = max;
		return this;
	}
	
	/**
	 * Create a copy of the current LinearScale
	 * @return
	 */
	public LinearScale copy() {
		return init().setDomain(getDomainMin(), getDomainMax())
				.setRange(getRangeMin(), getRangeMax());
	}
	
	/**
	 * Invert the current LinearScale
	 * @return
	 */
	public LinearScale invert() {
		double tempDomainMin = getDomainMin();
		double tempDomainMax = getDomainMax();
		setDomain(getRangeMin(), getRangeMax())
				.setRange(tempDomainMin, tempDomainMax);
		return this;
	}
	
	/**
	 * Get the Function of the current mapping
	 * @return
	 */
	public Function<Double, Double> getMapping() {
		return this.copy()::apply;
	}
	
	/**
	 * Get the inverted function of the current mapping
	 * @return
	 */
	public Function<Double, Double> getInvertedMapping() {
		return this.copy()::applyInverse;
	}
	
	/**
	 * Apply the current LinearScale to a number
	 * @param x
	 * @return
	 */
	public double apply(double x) {
		return ((rangeMax - rangeMin) * (x - domainMin)
				/ (domainMax - domainMin)) + rangeMin;
	}

	/**
	 * Apply the inverted LinearScale to a number. This method will not  
	 * modify the original object.
	 * @param x
	 * @return
	 */
	public double applyInverse(double x) {
		return this.copy().invert().apply(x);
	}
	
	@Override
	public int hashCode() {
		return OlognHashCode.init()
				.addDouble(domainMin)
				.addDouble(domainMax)
				.addDouble(rangeMin)
				.addDouble(rangeMax)
				.get();
	}
	
	@Override
	public boolean equals(Object obj) {
		return OlognHashCode.equals(this, obj,
				(a, b) -> a.getDomainMin() == b.getDomainMin()
				&& a.getDomainMax() == b.getDomainMax()
				&& a.getRangeMin() == b.getRangeMin()
				&& a.getRangeMax() == b.getRangeMax());
	}
	
	@Override
	public String toString() {
		return "LinearScale[domain: [" + getDomainMin() + ", "
				+ getDomainMax() + "], range: [" + getRangeMin() + ", "
				+ getRangeMax() + "]]"; 
	}
	
	/**
	 * Initialize a LinearScale object.
	 * @return
	 */
	public static LinearScale init() {
		return new LinearScale();
	}

}
