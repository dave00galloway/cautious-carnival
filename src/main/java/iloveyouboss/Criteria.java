package iloveyouboss;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Criteria implements Iterable<Criterion> {

    private final List<Criterion> criteria = new ArrayList<>();

    Criteria() {

    }

    public List<Criterion> getCriteria() {
        return criteria;
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }

    void add(Criterion criterion) {
        criteria.add(criterion);
    }
}
