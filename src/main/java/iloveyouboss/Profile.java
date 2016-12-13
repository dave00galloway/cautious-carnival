package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private int score;
    private String name;

    Profile(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    boolean matches(Criteria criteria) {
        score = 0;
        boolean kill = false;
        boolean anyMatches = false;
        for (Criterion criterion : criteria) {
            boolean match = false;
            Answer answer;
            if (criterion != null) {
                answer = answers.get(criterion.getAnswer().getQuestionText());
            } else {
                break;
            }
            if (answer != null) {
                match = criterion.getWeight() == Weight.DontCare || answer.match(criterion.getAnswer());
            }
            if (!match && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }
            if (match) {
                score += criterion.getWeight().getValue();
            }
            anyMatches |= match;
        }
        return !kill && anyMatches;

    }
}
