package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ProfileTest {

    private Profile profile;
    private Question question;
    private Criteria criteria;

    @Before
    public void setUp() throws Exception {
        profile = new Profile("Bull Hockey, Inc");
        question = new BooleanQuestion(1, "Got Bonuses?");
        criteria = new Criteria();
    }

    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        assertFalse(matches);
    }

    @Test
    public void matchAnswersTrueWhenMustMatchCriteriaAreMet() throws Exception {
        profile.add(new Answer(question, Bool.True));
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        assertTrue(matches);
    }

    @Test
    public void matchAnswersTrueWhenCriteriaCareNotIsNotMet() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.DontCare));
        boolean matches = profile.matches(criteria);
        assertTrue(matches);
    }

    @Test
    public void matchAnswersTrueWhenCriteriaCareNotIsMet() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(new Answer(question, Bool.False), Weight.DontCare));
        boolean matches = profile.matches(criteria);
        assertTrue(matches);
    }

    @Test
    public void matchAnswersFalseWhenDifferentQuestionIsAsked() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(new Answer(new BooleanQuestion(2, "some question"), Bool.False), Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        assertFalse(matches);
    }

    @Test(expected = NullPointerException.class)
    //todo:-move to criteriaTest.class
    public void criteriaThrowsNPEWhenNoQuestionIsAsked() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(new Answer(null, Bool.False), Weight.MustMatch));
        throw new Exception("Should have thrown an exception already!");
    }

    @Test(expected = NullPointerException.class)
    public void matchThrowsNPEWhenNullAnswerIsSupplied() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(null, Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        throw new IllegalAccessException(Boolean.toString(matches));
    }

    @Test
    public void matchReturnsFalseWhenNullCriterionIsSupplied() throws Exception {
        profile.add(new Answer(question, Bool.False));
        criteria.add(null);
        boolean matches = profile.matches(criteria);
        assertFalse(matches);
    }

    @Test
    public void matchAnswersTrueWhenMustMatchCriteriaAreMetAndCriteriaHoldsManyCriterion() throws Exception {
        profile.add(new Answer(question, Bool.True));
        for (int i = 0; i < 10; i++) {
            Bool aFalse;
            aFalse = (i % 2 == 0) ? Bool.False : Bool.True;
            criteria.add(new Criterion(new Answer(new BooleanQuestion(i, "some question " + i), aFalse), Weight.DontCare));
        }
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        assertTrue(matches);
    }

    @Test
    public void matchAnswersTrueWhenMustMatchCriteriaAreMetAndCriteriaHoldsManyMustMatchCriterion() throws Exception {
        profile.add(new Answer(question, Bool.True));
        for (int i = 0; i < 10; i++) {
            Bool aFalse;
            aFalse = (i % 2 == 0) ? Bool.False : Bool.True;
            Answer answer = new Answer(new BooleanQuestion(i, "some question " + i), aFalse);
            profile.add(answer);
            criteria.add(new Criterion(answer, Weight.MustMatch));
        }
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        assertTrue(matches);
    }

    @Test
    public void matchIncreasesProfileScore() {
        int preScore = profile.getScore();
        profile.add(new Answer(question, Bool.True));
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.MustMatch));
        profile.matches(criteria);
        int postScore = profile.getScore();
        assertThat(postScore).isGreaterThan(preScore);
    }

    @Test
    public void noMatchDoesNotIncreaseProfileScore() {
        int preScore = profile.getScore();
        profile.add(new Answer(question, Bool.False));
        criteria.add(new Criterion(new Answer(question, Bool.True), Weight.MustMatch));
        profile.matches(criteria);
        int postScore = profile.getScore();
        assertThat(postScore).isEqualTo(preScore);
    }

}