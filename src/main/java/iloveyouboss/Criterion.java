package iloveyouboss;

public class Criterion {
    private Answer answer;
    private Weight weight;

    public Criterion(Answer answer, Weight mustMatch) {

        this.answer = answer;
        weight = mustMatch;
    }

    Answer getAnswer() {
        return answer;
    }

    Weight getWeight() {
        return weight;
    }
}
