package com.github.brianrodewald.roughprojects.simpleelectioncalculator;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Appearable;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Individual;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.Party;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.elections.Election;
import com.github.brianrodewald.roughprojects.simpleelectioncalculator.entity.elections.FirstPastThePostElection;

import org.junit.Before;
import org.junit.BeforeClass;

public class FirstPastThePostElectionTest {
	
	private static final double DELTA = 1e-15; //for assertEquals(double,double,delta) method
	
	private static ArrayList<Individual> possibleCandidates;
	
	private static FirstPastThePostElection finishedOneCandidateElection;
	private static FirstPastThePostElection finishedThreeCandidateElection;
	private static FirstPastThePostElection finishedFourCandidateElection;
	
	private static FirstPastThePostElection noCandidateElection;
	private static FirstPastThePostElection incompleteTwoCandidateElection;
	private static FirstPastThePostElection tiedThreeCandidateElection;
	
	@BeforeClass
	public static void setUp() {
		possibleCandidates = new ArrayList<>();
		
		finishedOneCandidateElection = new FirstPastThePostElection("One candidate");
		finishedThreeCandidateElection = new FirstPastThePostElection("Three candidate");		
		finishedFourCandidateElection = new FirstPastThePostElection("Four candidate");
		
		noCandidateElection = new FirstPastThePostElection("No candidate");
		incompleteTwoCandidateElection = new FirstPastThePostElection("No votes cast");
		tiedThreeCandidateElection = new FirstPastThePostElection("Tied three candidate");
		
		Individual a = new Individual("Abraham", "Lincoln");
		Individual b = new Individual("Theodore", "Roosevelt");
		Individual c = new Individual("Franklin", "Roosevelt");
		Individual d = new Individual("George", "Washington");
		possibleCandidates.add(a);
		possibleCandidates.add(b);
		possibleCandidates.add(c);
		possibleCandidates.add(d);
		
		finishedOneCandidateElection.enterCandidateByName("Abraham", "Lincoln");
		finishedOneCandidateElection.enterCandidateByName("Abraham", "Lincoln");
		finishedOneCandidateElection.addResult(a,3);
		
		finishedThreeCandidateElection.addResult(a,3);
		finishedThreeCandidateElection.addResultByName("Theodore", "Roosevelt",4);
		finishedThreeCandidateElection.addResult(c,3);
		
		finishedFourCandidateElection.addResult(d,0);
		finishedFourCandidateElection.addResult(a,3);
		finishedFourCandidateElection.addResultByName("Theodore", "Roosevelt",4);
		finishedFourCandidateElection.addResult(c,3);
		finishedFourCandidateElection.enterCandidateByName("Theodore", "Roosevelt");
		
		
		incompleteTwoCandidateElection.enterCandidate(a);
		incompleteTwoCandidateElection.addResultByName("Theodore","Roosevelt",4);
		
		tiedThreeCandidateElection.addResult(a,3);
		tiedThreeCandidateElection.addResult(b,4);
		tiedThreeCandidateElection.addResult(c,4);
	}

	@Test
	public void test1() {
		assertEquals(0,noCandidateElection.getNumberOfCandidates());
	}
	
	@Test
	public void test2() {
		assertTrue(!noCandidateElection.finished());
		assertEquals(0,noCandidateElection.totalBallotsCast());
	}
	
	@Test(expected = NullPointerException.class)
	public void test_incomplete_election_not_finished() {
		assertTrue(!incompleteTwoCandidateElection.finished());
		incompleteTwoCandidateElection.getWinner();
	}
	
	@Test
	public void test_Unanimous_Victory() {
		assertTrue(finishedOneCandidateElection.finished());
		assertEquals(1,finishedOneCandidateElection.getNumberOfCandidates());
		assertEquals(possibleCandidates.get(0),finishedOneCandidateElection.getWinner());
	}
	
	@Test
	public void test_Simple_Election_Get_Winner() {
		assertTrue(finishedThreeCandidateElection.finished());
		assertEquals(3,finishedThreeCandidateElection.getNumberOfCandidates());
		assertEquals(possibleCandidates.get(1),finishedThreeCandidateElection.getWinner());
	}

	@Test
	public void testTiedElectionNotFinished() {
		assertTrue(tiedThreeCandidateElection.isTied());
		assertTrue(!tiedThreeCandidateElection.finished());
	}
	@Test(expected = NullPointerException.class)
	public void testTiedElectionNoWinner() {
		tiedThreeCandidateElection.getWinner();
	}
	
	@Test
	public void test_Get_Correct_Numbers() {
		assertEquals(3,finishedThreeCandidateElection.getNumericalResultByName("Franklin", "Roosevelt"));
	}
	
	@Test
	public void test_Get_Correct_Percentages() {
		assertEquals(30.0, finishedThreeCandidateElection.getPercentage(possibleCandidates.get(0)), DELTA);
		assertEquals(40.0, finishedThreeCandidateElection.getPercentage("Theodore","Roosevelt"), DELTA);
		assertEquals(30.0, finishedThreeCandidateElection.getPercentage(possibleCandidates.get(2)), DELTA);
	}
	@Test
	public void test_Get_Numerical_Result_For_Candidate_Doesnt_Exist() {
		assertEquals(-1,finishedThreeCandidateElection.getNumericalResultByName("John", "Smith"));
	}
	
	@Test
	public void test_String_Correct_Format() {
		String s = finishedFourCandidateElection.toString();
		String stringToFind = "Theodore Roosevelt (4 votes)";
		assertTrue(s.contains(stringToFind));
		stringToFind = "Winner: Theodore Roosevelt";
		assertTrue(s.contains(stringToFind));
	}
	@Test
	public void test_Incomplete_Election_Described() {
		String s = noCandidateElection.toString();
		assertTrue(s.contains("Cannot display result."));
	}
	
	@Test
	public void test_Tied_Election_Described() {
		String s = tiedThreeCandidateElection.toString();
		assertTrue(s.contains("Theodore Roosevelt and Franklin Roosevelt are tied"));
	}
	
}
