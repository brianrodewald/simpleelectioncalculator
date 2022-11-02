package com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity;

public class Individual implements Appearable, Comparable<Individual> {
	private final String firstName;
	private final String lastName;
	public Individual(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getName() {
		return firstName + " " + lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}

	
	public String toString() {
		return firstName + " " + lastName; 
	}
	@Override
	public int compareTo(Individual o) {
		// TODO Auto-generated method stub
		if (this.lastName.equals(o.lastName)) 
			return this.firstName.compareToIgnoreCase(o.firstName);
		return this.lastName.compareToIgnoreCase(o.lastName);
	}
	public boolean equals(Object o) {
		if (!(o instanceof Individual)) {
			return false;
		}
		return this.compareTo((Individual)o) == 0;
	}
	
}
