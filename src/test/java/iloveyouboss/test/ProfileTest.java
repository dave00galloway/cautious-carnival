package iloveyouboss.test;

import iloveyouboss.*;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ProfileTest {

    private Profile profile;
    private Question question;
    private Criteria criteria;

    //changed tests to use fixed question and answer fields. much clearer than the looping nonsense
    // I was using, which was a replacement for a question/answer manager class.
    // for a small number of tests, fixed fields is totally fine

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @Before
    public void setUp() throws Exception {
        profile = new Profile("Bull Hockey, Inc");
        question = new BooleanQuestion(1, "Got Bonuses?");
        criteria = new Criteria();
        createQuestionsAndAnswers();
    }

    private void createQuestionsAndAnswers() {
        questionIsThereRelocation =
                new BooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation =
                new Answer(questionIsThereRelocation, Bool.True);
        answerThereIsNoRelocation =
                new Answer(questionIsThereRelocation, Bool.False);

        questionReimbursesTuition =
                new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition =
                new Answer(questionReimbursesTuition, Bool.True);
        answerDoesNotReimburseTuition =
                new Answer(questionReimbursesTuition, Bool.False);

        questionOnsiteDaycare =
                new BooleanQuestion(1, "Onsite daycare?");
        answerHasOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.True);
        answerNoOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.False);
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
        profile.add(answerThereIsNoRelocation);
        profile.add(answerDoesNotReimburseTuition);
        profile.add(answerHasOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.VeryImportant));
        criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.MustMatch));
        boolean matches = profile.matches(criteria);
        assertTrue(matches);
    }

    @Test
    public void matchAnswersTrueWhenMustMatchCriteriaAreMetAndCriteriaHoldsManyMustMatchCriterion() throws Exception {
        profile.add(answerThereIsRelocation);
        profile.add(answerReimbursesTuition);
        profile.add(answerHasOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.MustMatch));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.MustMatch));
        criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.MustMatch));
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

    @Test
    public void scoreIsCriterionValueForSingleMatch() {
        profile.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        profile.matches(criteria);

        assertThat(profile.getScore()).isEqualTo(Weight.Important.getValue());
    }

    @Test
    public void scoreAccumulatesCriterionValuesForMatches() {
        profile.add(answerThereIsRelocation);
        profile.add(answerReimbursesTuition);
        profile.add(answerNoOnsiteDaycare);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.WouldPrefer));
        criteria.add(new Criterion(answerHasOnsiteDaycare, Weight.VeryImportant));

        profile.matches(criteria);

        int expectedScore = Weight.Important.getValue() + Weight.WouldPrefer.getValue();
        assertThat(profile.getScore()).isEqualTo(expectedScore);
    }

    @Test
    public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        boolean matches = profile.matches(criteria);

        assertTrue(matches);
    }

    @Test
    public void matchAnswersFalseWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerThereIsNoRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        boolean matches = profile.matches(criteria);

        assertFalse(matches);
    }

    @Test
    public void scoreIsZeroWhenThereAreNoMatches() {
        profile.add(answerThereIsNoRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        profile.matches(criteria);

        assertThat(profile.getScore()).isEqualTo(0);
    }
}