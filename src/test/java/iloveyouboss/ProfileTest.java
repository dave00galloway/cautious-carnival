package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

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

}