package com.damselfly.entity;

import java.util.ArrayList;
import java.util.List;

public class SecurityPortalCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SecurityPortalCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        public void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andLinkUrlIsNull() {
            addCriterion("link_url is null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlIsNotNull() {
            addCriterion("link_url is not null");
            return (Criteria) this;
        }

        public Criteria andLinkUrlEqualTo(String value) {
            addCriterion("link_url =", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotEqualTo(String value) {
            addCriterion("link_url <>", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlGreaterThan(String value) {
            addCriterion("link_url >", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlGreaterThanOrEqualTo(String value) {
            addCriterion("link_url >=", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlLessThan(String value) {
            addCriterion("link_url <", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlLessThanOrEqualTo(String value) {
            addCriterion("link_url <=", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlLike(String value) {
            addCriterion("link_url like", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotLike(String value) {
            addCriterion("link_url not like", value, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlIn(List<String> values) {
            addCriterion("link_url in", values, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotIn(List<String> values) {
            addCriterion("link_url not in", values, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlBetween(String value1, String value2) {
            addCriterion("link_url between", value1, value2, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andLinkUrlNotBetween(String value1, String value2) {
            addCriterion("link_url not between", value1, value2, "linkUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlIsNull() {
            addCriterion("json_url is null");
            return (Criteria) this;
        }

        public Criteria andJsonUrlIsNotNull() {
            addCriterion("json_url is not null");
            return (Criteria) this;
        }

        public Criteria andJsonUrlEqualTo(String value) {
            addCriterion("json_url =", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlNotEqualTo(String value) {
            addCriterion("json_url <>", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlGreaterThan(String value) {
            addCriterion("json_url >", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlGreaterThanOrEqualTo(String value) {
            addCriterion("json_url >=", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlLessThan(String value) {
            addCriterion("json_url <", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlLessThanOrEqualTo(String value) {
            addCriterion("json_url <=", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlLike(String value) {
            addCriterion("json_url like", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlNotLike(String value) {
            addCriterion("json_url not like", value, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlIn(List<String> values) {
            addCriterion("json_url in", values, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlNotIn(List<String> values) {
            addCriterion("json_url not in", values, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlBetween(String value1, String value2) {
            addCriterion("json_url between", value1, value2, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andJsonUrlNotBetween(String value1, String value2) {
            addCriterion("json_url not between", value1, value2, "jsonUrl");
            return (Criteria) this;
        }

        public Criteria andUrlTypeIsNull() {
            addCriterion("url_type is null");
            return (Criteria) this;
        }

        public Criteria andUrlTypeIsNotNull() {
            addCriterion("url_type is not null");
            return (Criteria) this;
        }

        public Criteria andUrlTypeEqualTo(Integer value) {
            addCriterion("url_type =", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeNotEqualTo(Integer value) {
            addCriterion("url_type <>", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeGreaterThan(Integer value) {
            addCriterion("url_type >", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("url_type >=", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeLessThan(Integer value) {
            addCriterion("url_type <", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeLessThanOrEqualTo(Integer value) {
            addCriterion("url_type <=", value, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeIn(List<Integer> values) {
            addCriterion("url_type in", values, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeNotIn(List<Integer> values) {
            addCriterion("url_type not in", values, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeBetween(Integer value1, Integer value2) {
            addCriterion("url_type between", value1, value2, "urlType");
            return (Criteria) this;
        }

        public Criteria andUrlTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("url_type not between", value1, value2, "urlType");
            return (Criteria) this;
        }

        public Criteria andShowTypeIsNull() {
            addCriterion("show_type is null");
            return (Criteria) this;
        }

        public Criteria andShowTypeIsNotNull() {
            addCriterion("show_type is not null");
            return (Criteria) this;
        }

        public Criteria andShowTypeEqualTo(Integer value) {
            addCriterion("show_type =", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeNotEqualTo(Integer value) {
            addCriterion("show_type <>", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeGreaterThan(Integer value) {
            addCriterion("show_type >", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("show_type >=", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeLessThan(Integer value) {
            addCriterion("show_type <", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeLessThanOrEqualTo(Integer value) {
            addCriterion("show_type <=", value, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeIn(List<Integer> values) {
            addCriterion("show_type in", values, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeNotIn(List<Integer> values) {
            addCriterion("show_type not in", values, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeBetween(Integer value1, Integer value2) {
            addCriterion("show_type between", value1, value2, "showType");
            return (Criteria) this;
        }

        public Criteria andShowTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("show_type not between", value1, value2, "showType");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andTableHeadIsNull() {
            addCriterion("table_head is null");
            return (Criteria) this;
        }

        public Criteria andTableHeadIsNotNull() {
            addCriterion("table_head is not null");
            return (Criteria) this;
        }

        public Criteria andTableHeadEqualTo(String value) {
            addCriterion("table_head =", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadNotEqualTo(String value) {
            addCriterion("table_head <>", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadGreaterThan(String value) {
            addCriterion("table_head >", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadGreaterThanOrEqualTo(String value) {
            addCriterion("table_head >=", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadLessThan(String value) {
            addCriterion("table_head <", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadLessThanOrEqualTo(String value) {
            addCriterion("table_head <=", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadLike(String value) {
            addCriterion("table_head like", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadNotLike(String value) {
            addCriterion("table_head not like", value, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadIn(List<String> values) {
            addCriterion("table_head in", values, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadNotIn(List<String> values) {
            addCriterion("table_head not in", values, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadBetween(String value1, String value2) {
            addCriterion("table_head between", value1, value2, "tableHead");
            return (Criteria) this;
        }

        public Criteria andTableHeadNotBetween(String value1, String value2) {
            addCriterion("table_head not between", value1, value2, "tableHead");
            return (Criteria) this;
        }

        public Criteria andIndexShowIsNull() {
            addCriterion("index_show is null");
            return (Criteria) this;
        }

        public Criteria andIndexShowIsNotNull() {
            addCriterion("index_show is not null");
            return (Criteria) this;
        }

        public Criteria andIndexShowEqualTo(Integer value) {
            addCriterion("index_show =", value, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowNotEqualTo(Integer value) {
            addCriterion("index_show <>", value, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowGreaterThan(Integer value) {
            addCriterion("index_show >", value, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("index_show >=", value, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowLessThan(Integer value) {
            addCriterion("index_show <", value, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowLessThanOrEqualTo(Integer value) {
            addCriterion("index_show <=", value, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowIn(List<Integer> values) {
            addCriterion("index_show in", values, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowNotIn(List<Integer> values) {
            addCriterion("index_show not in", values, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowBetween(Integer value1, Integer value2) {
            addCriterion("index_show between", value1, value2, "indexShow");
            return (Criteria) this;
        }

        public Criteria andIndexShowNotBetween(Integer value1, Integer value2) {
            addCriterion("index_show not between", value1, value2, "indexShow");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}