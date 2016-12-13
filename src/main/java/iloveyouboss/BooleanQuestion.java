package iloveyouboss;

class BooleanQuestion extends Question {
    BooleanQuestion(int id, String text) {
        super(text, new String[]{"No", "Yes"}, id);
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected == actual;
    }
}