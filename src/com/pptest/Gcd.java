package com.pptest;

import java.util.List;

public class Gcd {
	public int gcd() {
		// get 2 numbers from queue
		// delete and replace it
		// save to db
		final TestConnection test = new TestConnection();
		final List<Integer> listA = test.getGCDNumbers();
		return gcdCalc(listA.get(0), listA.get(1));
	}

	public List<Integer> gcdList() {
		final TestConnection test = new TestConnection();
		return test.getGCD();
	}

	public int gcdSum() {
		final TestConnection test = new TestConnection();
		return test.getGCDSum();
	}

	public int gcdCalc(int i1, int i2) {
		if (i2 == 0) {
			return i1;
		}
		return gcdCalc(i2, i1 % i2);
	}

}
