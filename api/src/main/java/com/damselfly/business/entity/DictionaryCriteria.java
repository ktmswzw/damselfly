package com.damselfly.business.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DictionaryCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DictionaryCriteria() {
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

        protected void addCriterion(String condition) {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andPkIdIsNull() {
            addCriterion("pk_id is null");
            return (Criteria) this;
        }

        public Criteria andPkIdIsNotNull() {
            addCriterion("pk_id is not null");
            return (Criteria) this;
        }

        public Criteria andPkIdEqualTo(Integer value) {
            addCriterion("pk_id =", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdNotEqualTo(Integer value) {
            addCriterion("pk_id <>", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdGreaterThan(Integer value) {
            addCriterion("pk_id >", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("pk_id >=", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdLessThan(Integer value) {
            addCriterion("pk_id <", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdLessThanOrEqualTo(Integer value) {
            addCriterion("pk_id <=", value, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdIn(List<Integer> values) {
            addCriterion("pk_id in", values, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdNotIn(List<Integer> values) {
            addCriterion("pk_id not in", values, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdBetween(Integer value1, Integer value2) {
            addCriterion("pk_id between", value1, value2, "pkId");
            return (Criteria) this;
        }

        public Criteria andPkIdNotBetween(Integer value1, Integer value2) {
            addCriterion("pk_id not between", value1, value2, "pkId");
            return (Criteria) this;
        }

        public Criteria andDicIdIsNull() {
            addCriterion("dic_id is null");
            return (Criteria) this;
        }

        public Criteria andDicIdIsNotNull() {
            addCriterion("dic_id is not null");
            return (Criteria) this;
        }

        public Criteria andDicIdEqualTo(Integer value) {
            addCriterion("dic_id =", value, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdNotEqualTo(Integer value) {
            addCriterion("dic_id <>", value, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdGreaterThan(Integer value) {
            addCriterion("dic_id >", value, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dic_id >=", value, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdLessThan(Integer value) {
            addCriterion("dic_id <", value, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdLessThanOrEqualTo(Integer value) {
            addCriterion("dic_id <=", value, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdIn(List<Integer> values) {
            addCriterion("dic_id in", values, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdNotIn(List<Integer> values) {
            addCriterion("dic_id not in", values, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdBetween(Integer value1, Integer value2) {
            addCriterion("dic_id between", value1, value2, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dic_id not between", value1, value2, "dicId");
            return (Criteria) this;
        }

        public Criteria andDicNameIsNull() {
            addCriterion("dic_name is null");
            return (Criteria) this;
        }

        public Criteria andDicNameIsNotNull() {
            addCriterion("dic_name is not null");
            return (Criteria) this;
        }

        public Criteria andDicNameEqualTo(String value) {
            addCriterion("dic_name =", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameNotEqualTo(String value) {
            addCriterion("dic_name <>", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameGreaterThan(String value) {
            addCriterion("dic_name >", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameGreaterThanOrEqualTo(String value) {
            addCriterion("dic_name >=", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameLessThan(String value) {
            addCriterion("dic_name <", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameLessThanOrEqualTo(String value) {
            addCriterion("dic_name <=", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameLike(String value) {
            addCriterion("dic_name like", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameNotLike(String value) {
            addCriterion("dic_name not like", value, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameIn(List<String> values) {
            addCriterion("dic_name in", values, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameNotIn(List<String> values) {
            addCriterion("dic_name not in", values, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameBetween(String value1, String value2) {
            addCriterion("dic_name between", value1, value2, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicNameNotBetween(String value1, String value2) {
            addCriterion("dic_name not between", value1, value2, "dicName");
            return (Criteria) this;
        }

        public Criteria andDicCodeIsNull() {
            addCriterion("dic_code is null");
            return (Criteria) this;
        }

        public Criteria andDicCodeIsNotNull() {
            addCriterion("dic_code is not null");
            return (Criteria) this;
        }

        public Criteria andDicCodeEqualTo(String value) {
            addCriterion("dic_code =", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeNotEqualTo(String value) {
            addCriterion("dic_code <>", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeGreaterThan(String value) {
            addCriterion("dic_code >", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dic_code >=", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeLessThan(String value) {
            addCriterion("dic_code <", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeLessThanOrEqualTo(String value) {
            addCriterion("dic_code <=", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeLike(String value) {
            addCriterion("dic_code like", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeNotLike(String value) {
            addCriterion("dic_code not like", value, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeIn(List<String> values) {
            addCriterion("dic_code in", values, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeNotIn(List<String> values) {
            addCriterion("dic_code not in", values, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeBetween(String value1, String value2) {
            addCriterion("dic_code between", value1, value2, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicCodeNotBetween(String value1, String value2) {
            addCriterion("dic_code not between", value1, value2, "dicCode");
            return (Criteria) this;
        }

        public Criteria andDicParentIdIsNull() {
            addCriterion("dic_parent_id is null");
            return (Criteria) this;
        }

        public Criteria andDicParentIdIsNotNull() {
            addCriterion("dic_parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andDicParentIdEqualTo(Integer value) {
            addCriterion("dic_parent_id =", value, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdNotEqualTo(Integer value) {
            addCriterion("dic_parent_id <>", value, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdGreaterThan(Integer value) {
            addCriterion("dic_parent_id >", value, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dic_parent_id >=", value, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdLessThan(Integer value) {
            addCriterion("dic_parent_id <", value, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("dic_parent_id <=", value, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdIn(List<Integer> values) {
            addCriterion("dic_parent_id in", values, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdNotIn(List<Integer> values) {
            addCriterion("dic_parent_id not in", values, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdBetween(Integer value1, Integer value2) {
            addCriterion("dic_parent_id between", value1, value2, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dic_parent_id not between", value1, value2, "dicParentId");
            return (Criteria) this;
        }

        public Criteria andDicKeyIsNull() {
            addCriterion("dic_key is null");
            return (Criteria) this;
        }

        public Criteria andDicKeyIsNotNull() {
            addCriterion("dic_key is not null");
            return (Criteria) this;
        }

        public Criteria andDicKeyEqualTo(String value) {
            addCriterion("dic_key =", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyNotEqualTo(String value) {
            addCriterion("dic_key <>", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyGreaterThan(String value) {
            addCriterion("dic_key >", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyGreaterThanOrEqualTo(String value) {
            addCriterion("dic_key >=", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyLessThan(String value) {
            addCriterion("dic_key <", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyLessThanOrEqualTo(String value) {
            addCriterion("dic_key <=", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyLike(String value) {
            addCriterion("dic_key like", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyNotLike(String value) {
            addCriterion("dic_key not like", value, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyIn(List<String> values) {
            addCriterion("dic_key in", values, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyNotIn(List<String> values) {
            addCriterion("dic_key not in", values, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyBetween(String value1, String value2) {
            addCriterion("dic_key between", value1, value2, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicKeyNotBetween(String value1, String value2) {
            addCriterion("dic_key not between", value1, value2, "dicKey");
            return (Criteria) this;
        }

        public Criteria andDicValueIsNull() {
            addCriterion("dic_value is null");
            return (Criteria) this;
        }

        public Criteria andDicValueIsNotNull() {
            addCriterion("dic_value is not null");
            return (Criteria) this;
        }

        public Criteria andDicValueEqualTo(String value) {
            addCriterion("dic_value =", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueNotEqualTo(String value) {
            addCriterion("dic_value <>", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueGreaterThan(String value) {
            addCriterion("dic_value >", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueGreaterThanOrEqualTo(String value) {
            addCriterion("dic_value >=", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueLessThan(String value) {
            addCriterion("dic_value <", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueLessThanOrEqualTo(String value) {
            addCriterion("dic_value <=", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueLike(String value) {
            addCriterion("dic_value like", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueNotLike(String value) {
            addCriterion("dic_value not like", value, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueIn(List<String> values) {
            addCriterion("dic_value in", values, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueNotIn(List<String> values) {
            addCriterion("dic_value not in", values, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueBetween(String value1, String value2) {
            addCriterion("dic_value between", value1, value2, "dicValue");
            return (Criteria) this;
        }

        public Criteria andDicValueNotBetween(String value1, String value2) {
            addCriterion("dic_value not between", value1, value2, "dicValue");
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

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateTimeIsNull() {
            addCriterion("state_time is null");
            return (Criteria) this;
        }

        public Criteria andStateTimeIsNotNull() {
            addCriterion("state_time is not null");
            return (Criteria) this;
        }

        public Criteria andStateTimeEqualTo(Date value) {
            addCriterionForJDBCDate("state_time =", value, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("state_time <>", value, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("state_time >", value, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("state_time >=", value, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeLessThan(Date value) {
            addCriterionForJDBCDate("state_time <", value, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("state_time <=", value, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeIn(List<Date> values) {
            addCriterionForJDBCDate("state_time in", values, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("state_time not in", values, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("state_time between", value1, value2, "stateTime");
            return (Criteria) this;
        }

        public Criteria andStateTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("state_time not between", value1, value2, "stateTime");
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