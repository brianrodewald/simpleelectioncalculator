package com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity;

import java.util.TreeSet;

public class Party implements Appearable, Comparable<Party>{
	public final String name;
	public final String description;
	private final TreeSet<Individual> members = new TreeSet<>();
	
	public Party(String name) {
		this.name = name;
		this.description = name + " description";
	}
	
	public Party(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	public Party(String name, String description, String firstName, String lastName) {
		this.name = name;
		this.description = description;
		members.add(new Individual(firstName, lastName));
	}
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean addMember(String firstName, String lastName) { 
		return members.add(new Individual(firstName, lastName));
	}
	
	public boolean isInList(String firstName, String lastName) {
		return members.contains(new Individual(firstName, lastName));
	}
	
	public TreeSet<Individual> getMembers() {
		return members;
	}
	

	public boolean equals(Object o) {
		if (!(o instanceof Party)) {
			return false;
		}
		Party other = (Party)o;
		if (this.name != other.name) {
			return false;
		}
		return this.members.containsAll(other.members);
	}

	@Override
	public int compareTo(Party o) {
		return this.name.compareTo(o.name);
	}
	
}
