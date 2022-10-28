package co.empathy.academy.demo_search.query;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SingleCompoundQuery implements Query {
    protected Query compound;

    public SingleCompoundQuery(Query compound) {
        this.compound = compound;
    }

    protected abstract String queryName();

    @Override
    public JSONObject make() {
        return new JSONObject()
                .put(queryName(), compound.make());
    }
}
