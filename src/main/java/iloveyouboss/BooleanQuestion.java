package iloveyouboss;

import iloveyouboss.domain.Persistable;

import java.time.Instant;

public class BooleanQuestion extends Question implements Persistable {
    public BooleanQuestion(int id, String text) {
        super(text, new String[]{"No", "Yes"}, id);
    }

    public BooleanQuestion(String text) {
        super(text);
    }

    @Override
    public boolean match(int expected, int actual) {
        return expected == actual;
    }

    @Override
    public void setCreateTimeStamp(Instant instant) {

    }
}
