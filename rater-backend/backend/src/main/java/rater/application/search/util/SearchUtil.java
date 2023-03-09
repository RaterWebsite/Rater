package rater.application.search.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import rater.application.search.SearchRequestDTO;

public class SearchUtil {
    private SearchUtil() {
    }

    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto) {

        try {
            final int page = dto.getPage();
            final int size = dto.getSize();
            final int from = page <= 0 ? 0 : page * size;

            SearchSourceBuilder builder = new SearchSourceBuilder().from(from).size(size)
                    .postFilter(getQueryBuilder(dto));

            if (dto.getSortBy() != null) {
                builder = builder.sort(
                        dto.getSortBy(),
                        dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
            }

            final SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    
    public static SearchRequest buildSearchRequest(final String indexName, Map<String, Float> categoriesBoost) {
        try {
            String code = 
                "double score = 100000.0;"
                + "for (def category : params.categories.keySet()) {"
                + "    String categoryLower = category.toLowerCase();"
                + "    if (categoryLower.equals(\"familyfriendly\")) {"
                + "           categoryLower = \"familyFriendly\";"
                + "    }"
                + "    score += doc['categories.' + categoryLower].value * params.categories[category];"
                + "}"
                + "return score;"; 
            Map<String, Object> params = Collections.singletonMap("categories", categoriesBoost);
            // create the function score query  
            Script script = new Script(ScriptType.INLINE, "painless", code, params);
            ScriptScoreQueryBuilder scriptScoreQueryBuilder = QueryBuilders.scriptScoreQuery(QueryBuilders.matchAllQuery(), script);

            // add additional query parameters as needed
            // ...

            // create the search request and return it
            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(scriptScoreQueryBuilder);
            searchRequest.source(searchSourceBuilder);
            return searchRequest;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildSearchRequest(final String indexName, final String field, final Date date) {
        try {
            final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));
            final SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static QueryBuilder getQueryBuilder(final SearchRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        final List<String> fields = dto.getFields();
        if (CollectionUtils.isEmpty(fields)) {
            return null;
        }

        if (fields.size() > 1) {
            final MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.AND);
            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }

        return fields.stream().findFirst()
                .map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm()).operator(Operator.AND)).orElse(null);
    }

    private static QueryBuilder getQueryBuilder(final String field, final Date date) {
        return QueryBuilders.rangeQuery(field).gte(date);
    }

}
