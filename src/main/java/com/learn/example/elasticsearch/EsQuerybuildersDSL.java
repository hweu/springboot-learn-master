package com.learn.example.elasticsearch;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class EsQuerybuildersDSL {
    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * match query 单个匹配
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder matchQuery(String field,String searchContent) {
        return QueryBuilders.matchQuery(field, searchContent);
    }
    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * multimatch  query
     * 创建一个匹配查询的布尔型提供字段名称和文本。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder multiMatchQuery(String[] fields,String searchContent) {
        //现住址和家乡在【山西省太原市7429街道】的人
        return QueryBuilders.multiMatchQuery(
                searchContent,     // Text you are looking for
                fields       // Fields you query on
        );
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * boolean query and 条件组合查询
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder booleanQuery() {

        return QueryBuilders
                .boolQuery()
                .must(QueryBuilders.termQuery("name", "葫芦3033娃"))
                .must(QueryBuilders.termQuery("home", "山西省太原市7967街道"))
                .mustNot(QueryBuilders.termQuery("isRealMen", false))
                .should(QueryBuilders.termQuery("now_home", "山西省太原市"));
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * ids query
     * 构造一个只会匹配的特定数据 id 的查询。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder idsQuery(String[] ids) {
        return QueryBuilders.idsQuery().addIds(ids);
    }

    /**
     * TODO NotSolved
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * constant score query
     * 另一个查询和查询,包裹查询只返回一个常数分数等于提高每个文档的查询。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder constantScoreQuery(String field,String searchContent) {
        /*return // Using with Filters
                QueryBuilders.constantScoreQuery(FilterBuilders.termFilter("name", "kimchy"))
                        .boost(2.0f);*/

        // With Queries
        return QueryBuilders.constantScoreQuery(QueryBuilders.termQuery(field, searchContent))
                .boost(2.0f);
    }

    /**
     * TODO NotSolved
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * disjunction max query
     * 一个生成的子查询文件产生的联合查询，
     * 而且每个分数的文件具有最高得分文件的任何子查询产生的，
     * 再加上打破平手的增加任何额外的匹配的子查询。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder disMaxQuery() {
        return QueryBuilders.disMaxQuery()
                .add(QueryBuilders.termQuery("name", "kimchy"))          // Your queries
                .add(QueryBuilders.termQuery("name", "elasticsearch"))   // Your queries
                .boost(1.2f)
                .tieBreaker(0.7f);
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * fuzzy query
     * 使用模糊查询匹配文档查询。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder fuzzyQuery(String field,String searchContent) {
        return QueryBuilders.fuzzyQuery(field, searchContent);
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * matchall query
     * 查询匹配所有文件。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder matchAllQuery() {
        return QueryBuilders.matchAllQuery();
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * prefix query
     * 包含与查询相匹配的文档指定的前缀。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder prefixQuery(String field,String searchContent) {
        return QueryBuilders.prefixQuery(field, searchContent);
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * range query
     * 查询相匹配的文档在一个范围。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder rangeQuery() {
        return QueryBuilders
                .rangeQuery("name")
                .from("葫芦1000娃")
                .to("葫芦3000娃")
                .includeLower(true)     //包括下界
                .includeUpper(false); //包括上界
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * term query
     * 一个查询相匹配的文件包含一个术语。。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder termQuery(String field,String searchContent) {
        return QueryBuilders.termQuery(field, searchContent);
    }

    /**
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * wildcard query
     * 　　实现了通配符搜索查询。支持通配符* < /tt>,<tt>
     * 　　匹配任何字符序列(包括空),<tt> ? < /tt>,
     * 　　匹配任何单个的字符。注意该查询可以缓慢,因为它
     * 　　许多方面需要遍历。为了防止WildcardQueries极其缓慢。
     * 　　一个通配符词不应该从一个通配符* < /tt>或<tt>
     * 　　< /tt> <tt> ?。
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    public static QueryBuilder wildcardQuery(String field,String searchContent) {
        return QueryBuilders.wildcardQuery(field, searchContent);
    }

}
