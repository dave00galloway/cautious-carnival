package iloveyouboss;

class Answer {

    private final Bool answerValue;
    private String questionText;

    Answer(Question question, Bool aBoolean) {
        answerValue = aBoolean;
        questionText = question.getText();
    }

    String getQuestionText() {
        return questionText;
    }

    boolean match(Answer answer) {
        return answerValue.equals(answer.answerValue);
    }
}
