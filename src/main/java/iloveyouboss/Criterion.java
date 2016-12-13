package iloveyouboss;

class Criterion {
    private Answer answer;
    private Weight weight;

    Criterion(Answer answer, Weight mustMatch) {

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
