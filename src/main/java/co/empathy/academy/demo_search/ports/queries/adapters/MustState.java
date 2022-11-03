package co.empathy.academy.demo_search.ports.queries.adapters;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

public class MustState implements ElasticQueryBuilderState {

    private final BoolQuery.Builder builder;

    public MustState() {
        this.builder = QueryBuilders.bool();
    }

    @Override
    public ElasticQueryBuilderState must() {
        throw new ElasticStateException("Must", "must");
    }

    @Override
    public ElasticQueryBuilderState match(String field, String value) {
        this.builder.must(
                t -> t.match(
                        m -> m.field(field).query(value)
                )
        );
        return this;
    }

    @Override
    public Query build() {
        return builder.build()._toQuery();
    }
}
