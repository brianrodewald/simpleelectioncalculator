package com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.elections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Appearable;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Individual;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Party;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.results.Result;

public abstract class Election<T extends Appearable> {
	private String name;
	private LocalDate date;
	private final ArrayList<Result<T>> results = new ArrayList<>();
	
	private final Comparator<Result<T>> voteSpread = ((c, d) -> -(c.compareTo(d))); //change to descending order of votes
	
	protected void setName(String n) {
		this.name = n;
	}
	
	protected void setDate(LocalDate d) {
		this.date = d;
	}
	
	protected int getNumericalResult(T a) {
		Collections.sort(results, voteSpread);
		for (Result<?> r : results) {
			if (r.getEntity().equals(a)) {
				return r.getVotes();
			}
		}
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	protected T getCandidateByPosition(int pos) {
		if (!finished() && !(isTied())) throw new NullPointerException();
		Collections.sort(results, voteSpread);
		return (T) results.get(pos).getEntity();
	}

	protected boolean enterCandidate(T candidate) {
		for (Result<?> r : results) {
			if (r.getName().equals(candidate.getName())) {
				return false;
			}
		}
		boolean b = results.add(new Result<T>(candidate, -1));
		Collections.sort(results, voteSpread);
		return b;
	}
	
	protected boolean addResult(T candidate, Integer votes) {
		for (Result<?> r : results) {
			if (r.getName().equals(candidate.getName())) {
				results.remove(r);
				break;
			}
		}
		boolean b = results.add(new Result<T>(candidate, votes));
		Collections.sort(results, voteSpread);
		return b;
	}
	
	
	public int getNumberOfCandidates() {
		return results.size();
	}
	
	public boolean finished() {
		return (results.size() != 0) && results.stream().noneMatch(r -> r.getVotes() < 0) && !isTied();
	}
	
	public int totalBallotsCast() {
		if (finished()) {
			return results.stream().collect(Collectors.summingInt(Result::getVotes));
		}
		return 0;
	}
	
	public String toString() {
		Collections.sort(results, voteSpread);
		String s = date + " " + name + " election" + "\n";
		if (finished() || isTied()) {
			for (Result<?> r: results) {
				s += r + "\n";
			}
			if (isTied()) {
				s += "No winner determined.\n" +
						getCandidateByPosition(0).getName() + " and " + getCandidateByPosition(1).getName() + " are tied\n";
			} else {
				s += "Winner: " + getCandidateByPosition(0).getName() + "\n";
			}
		} else {
			s += "Not all votes counted.\n Cannot display result.\n";
		}
		return s;
	}
	
	public boolean isTied() {
		Collections.sort(results, voteSpread);
		return (results.size() >= 2) && (results.get(0).getVotes() == results.get(1).getVotes());
	}
	
	public abstract Appearable getWinner() throws NullPointerException;
	public abstract double getPercentage(T a) throws NullPointerException;
	
	
}
