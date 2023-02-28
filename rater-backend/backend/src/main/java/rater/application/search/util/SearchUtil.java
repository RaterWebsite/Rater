package rater.application.search.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.script.Script;
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
            SearchRequest searchRequest = new SearchRequest(indexName);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        
            final BoolQueryBuilder queryBuilder = new BoolQueryBuilder();

            for (Map.Entry<String, Float> category : categoriesBoost.entrySet()) {
                String fieldName = "categories." + category.getKey().toLowerCase(); //gets "categories.categoryName", the name of the category in the json doc
                if (fieldName.equals("categories.familyfriendly")) {
                    fieldName = "categories.familyFriendly";
                }
                RangeQueryBuilder rangeQuery = new RangeQueryBuilder(fieldName);
                Float boost = category.getValue();
                if (boost == 0) {
                    continue;
                }
                if (boost < 0) {
                    if (boost < -0.5) {
                        queryBuilder.should(new RangeQueryBuilder(fieldName).lte(4));
                        queryBuilder.should(new RangeQueryBuilder(fieldName).lte(3));
                        queryBuilder.should(new RangeQueryBuilder(fieldName).lte(2));
                    }
                    queryBuilder.should(new RangeQueryBuilder(fieldName).lte(5));
                    queryBuilder.should(new RangeQueryBuilder(fieldName).lte(6));
                } else {
                    if (boost > 0.5) {
                        queryBuilder.should(new RangeQueryBuilder(fieldName).gte(9));
                        queryBuilder.must(new RangeQueryBuilder(fieldName).gte(8));
                    } 
                    queryBuilder.must(new RangeQueryBuilder(fieldName).gte(7));
                }
                //TODO: right now, we don't want to use category.value since that will only be 0.5, 1, or 1.5 (most movies will be above that range)
                //in the future, we want to fine tune the gte value, but for now, if the category is listed, we will assume just make it gte 7
                //queryBuilder.should(rangeQuery);
            }
        
            // set query and sort
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.DESC));
            searchSourceBuilder.from(0);
            searchSourceBuilder.size(10);
        
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
