package co.empathy.academy.demo_search.query;

public class Match extends SingleCompoundQuery {
    public Match(Query compound) {
        super(compound);
    }

    @Override
    protected String queryName() {
        return "match";
    }
}
