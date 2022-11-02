package com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.elections;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Appearable;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Individual;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.results.Result;

public final class FirstPastThePostElection extends Election<Individual> {
	public FirstPastThePostElection(String name) {
		super();
		setName(name);
		setDate(LocalDate.now());
	}
	
	public int getNumericalResultByName(String firstName, String lastName) {
		Individual c = new Individual(firstName, lastName);
		return getNumericalResult(c);
	}
	
	public boolean enterCandidateByName(String firstName, String lastName) {
		Individual c = new Individual(firstName, lastName);
		return enterCandidate(c);
	}
	
	public boolean addResultByName(String firstName, String lastName, int votes) {
		Individual c = new Individual(firstName, lastName);
		return addResult(c, votes);
	}
	
	public boolean enterCandidate(Individual candidate) {
		return super.enterCandidate(candidate);
	}
	
	public boolean addResult(Individual candidate, int votes) {
		return super.addResult(candidate, votes);
	}

	@Override
	public Individual getWinner() throws NullPointerException {
		if (!isTied())
			return getCandidateByPosition(0);
		throw new NullPointerException();
	}

	@Override
	public double getPercentage(Individual a) throws NullPointerException {
		return ((double) getNumericalResult(a) / (double) totalBallotsCast()) * 100;
	}
	
	public double getPercentage(String firstName, String lastName) throws NullPointerException {
		return getPercentage(new Individual(firstName,lastName));
	}
	

	
}
