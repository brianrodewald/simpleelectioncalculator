package com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.results;

import java.util.Objects;

import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Appearable;

public class Result<T extends Appearable> implements Comparable<Result<T>> {
	protected final Appearable candidate;
	protected final int votes;
	
	public Result(Appearable candidate, int votes) {
		super();
		this.candidate = candidate;
		this.votes = votes;
	}
	public final String getName() {
		return candidate.getName();
	}
	public Appearable getEntity() {
		return candidate;
	}
	public final int getVotes() {
		return votes;
	}

	public int compareTo(Result<T> other) {
		if (this.votes == other.votes)
			return this.getName().compareTo(other.getName());
		return this.votes - other.votes;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(candidate, votes);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Result<?>)) return false;
		Result<T> other = (Result<T>) obj;
		return this.compareTo(other) == 0;
	}
	
	public int voteSpread(Result<T> o) {
		return this.votes - o.votes;
	}
	
	public String toString() {
		return this.getName() + " (" + this.votes + " votes)";
	}
}
