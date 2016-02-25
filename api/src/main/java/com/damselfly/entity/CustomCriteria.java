package com.damselfly.entity;

import java.util.ArrayList;
import java.util.List;

public class CustomCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomCriteria() {
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

        public Criteria andCust_idIsNull() {
            addCriterion("cust_id is null");
            return (Criteria) this;
        }

        public Criteria andCust_idIsNotNull() {
            addCriterion("cust_id is not null");
            return (Criteria) this;
        }

        public Criteria andCust_idEqualTo(Integer value) {
            addCriterion("cust_id =", value, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idNotEqualTo(Integer value) {
            addCriterion("cust_id <>", value, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idGreaterThan(Integer value) {
            addCriterion("cust_id >", value, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("cust_id >=", value, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idLessThan(Integer value) {
            addCriterion("cust_id <", value, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idLessThanOrEqualTo(Integer value) {
            addCriterion("cust_id <=", value, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idIn(List<Integer> values) {
            addCriterion("cust_id in", values, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idNotIn(List<Integer> values) {
            addCriterion("cust_id not in", values, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idBetween(Integer value1, Integer value2) {
            addCriterion("cust_id between", value1, value2, "cust_id");
            return (Criteria) this;
        }

        public Criteria andCust_idNotBetween(Integer value1, Integer value2) {
            addCriterion("cust_id not between", value1, value2, "cust_id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("cust_name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("cust_name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("cust_name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("cust_name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("cust_name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("cust_name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("cust_name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("cust_name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("cust_name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("cust_name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("cust_name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("cust_name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("cust_name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("cust_name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andAddrIsNull() {
            addCriterion("cust_addr is null");
            return (Criteria) this;
        }

        public Criteria andAddrIsNotNull() {
            addCriterion("cust_addr is not null");
            return (Criteria) this;
        }

        public Criteria andAddrEqualTo(String value) {
            addCriterion("cust_addr =", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotEqualTo(String value) {
            addCriterion("cust_addr <>", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrGreaterThan(String value) {
            addCriterion("cust_addr >", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrGreaterThanOrEqualTo(String value) {
            addCriterion("cust_addr >=", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLessThan(String value) {
            addCriterion("cust_addr <", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLessThanOrEqualTo(String value) {
            addCriterion("cust_addr <=", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrLike(String value) {
            addCriterion("cust_addr like", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotLike(String value) {
            addCriterion("cust_addr not like", value, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrIn(List<String> values) {
            addCriterion("cust_addr in", values, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotIn(List<String> values) {
            addCriterion("cust_addr not in", values, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrBetween(String value1, String value2) {
            addCriterion("cust_addr between", value1, value2, "addr");
            return (Criteria) this;
        }

        public Criteria andAddrNotBetween(String value1, String value2) {
            addCriterion("cust_addr not between", value1, value2, "addr");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("cust_code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("cust_code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("cust_code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("cust_code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("cust_code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("cust_code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("cust_code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("cust_code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("cust_code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("cust_code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("cust_code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("cust_code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("cust_code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("cust_code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("cust_level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("cust_level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("cust_level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("cust_level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("cust_level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("cust_level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("cust_level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("cust_level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("cust_level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("cust_level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("cust_level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("cust_level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNull() {
            addCriterion("industry_type is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNotNull() {
            addCriterion("industry_type is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryEqualTo(Integer value) {
            addCriterion("industry_type =", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotEqualTo(Integer value) {
            addCriterion("industry_type <>", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThan(Integer value) {
            addCriterion("industry_type >", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThanOrEqualTo(Integer value) {
            addCriterion("industry_type >=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThan(Integer value) {
            addCriterion("industry_type <", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThanOrEqualTo(Integer value) {
            addCriterion("industry_type <=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryIn(List<Integer> values) {
            addCriterion("industry_type in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotIn(List<Integer> values) {
            addCriterion("industry_type not in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryBetween(Integer value1, Integer value2) {
            addCriterion("industry_type between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotBetween(Integer value1, Integer value2) {
            addCriterion("industry_type not between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNull() {
            addCriterion("zip_code is null");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNotNull() {
            addCriterion("zip_code is not null");
            return (Criteria) this;
        }

        public Criteria andZipCodeEqualTo(String value) {
            addCriterion("zip_code =", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotEqualTo(String value) {
            addCriterion("zip_code <>", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThan(String value) {
            addCriterion("zip_code >", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zip_code >=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThan(String value) {
            addCriterion("zip_code <", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThanOrEqualTo(String value) {
            addCriterion("zip_code <=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLike(String value) {
            addCriterion("zip_code like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotLike(String value) {
            addCriterion("zip_code not like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeIn(List<String> values) {
            addCriterion("zip_code in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotIn(List<String> values) {
            addCriterion("zip_code not in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeBetween(String value1, String value2) {
            addCriterion("zip_code between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotBetween(String value1, String value2) {
            addCriterion("zip_code not between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andWebAddrIsNull() {
            addCriterion("web_addr is null");
            return (Criteria) this;
        }

        public Criteria andWebAddrIsNotNull() {
            addCriterion("web_addr is not null");
            return (Criteria) this;
        }

        public Criteria andWebAddrEqualTo(String value) {
            addCriterion("web_addr =", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrNotEqualTo(String value) {
            addCriterion("web_addr <>", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrGreaterThan(String value) {
            addCriterion("web_addr >", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrGreaterThanOrEqualTo(String value) {
            addCriterion("web_addr >=", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrLessThan(String value) {
            addCriterion("web_addr <", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrLessThanOrEqualTo(String value) {
            addCriterion("web_addr <=", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrLike(String value) {
            addCriterion("web_addr like", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrNotLike(String value) {
            addCriterion("web_addr not like", value, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrIn(List<String> values) {
            addCriterion("web_addr in", values, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrNotIn(List<String> values) {
            addCriterion("web_addr not in", values, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrBetween(String value1, String value2) {
            addCriterion("web_addr between", value1, value2, "webAddr");
            return (Criteria) this;
        }

        public Criteria andWebAddrNotBetween(String value1, String value2) {
            addCriterion("web_addr not between", value1, value2, "webAddr");
            return (Criteria) this;
        }

        public Criteria andCManIsNull() {
            addCriterion("charge_man is null");
            return (Criteria) this;
        }

        public Criteria andCManIsNotNull() {
            addCriterion("charge_man is not null");
            return (Criteria) this;
        }

        public Criteria andCManEqualTo(String value) {
            addCriterion("charge_man =", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManNotEqualTo(String value) {
            addCriterion("charge_man <>", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManGreaterThan(String value) {
            addCriterion("charge_man >", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManGreaterThanOrEqualTo(String value) {
            addCriterion("charge_man >=", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManLessThan(String value) {
            addCriterion("charge_man <", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManLessThanOrEqualTo(String value) {
            addCriterion("charge_man <=", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManLike(String value) {
            addCriterion("charge_man like", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManNotLike(String value) {
            addCriterion("charge_man not like", value, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManIn(List<String> values) {
            addCriterion("charge_man in", values, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManNotIn(List<String> values) {
            addCriterion("charge_man not in", values, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManBetween(String value1, String value2) {
            addCriterion("charge_man between", value1, value2, "cMan");
            return (Criteria) this;
        }

        public Criteria andCManNotBetween(String value1, String value2) {
            addCriterion("charge_man not between", value1, value2, "cMan");
            return (Criteria) this;
        }

        public Criteria andCMPhoneIsNull() {
            addCriterion("c_mobile_phone is null");
            return (Criteria) this;
        }

        public Criteria andCMPhoneIsNotNull() {
            addCriterion("c_mobile_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCMPhoneEqualTo(String value) {
            addCriterion("c_mobile_phone =", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneNotEqualTo(String value) {
            addCriterion("c_mobile_phone <>", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneGreaterThan(String value) {
            addCriterion("c_mobile_phone >", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("c_mobile_phone >=", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneLessThan(String value) {
            addCriterion("c_mobile_phone <", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneLessThanOrEqualTo(String value) {
            addCriterion("c_mobile_phone <=", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneLike(String value) {
            addCriterion("c_mobile_phone like", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneNotLike(String value) {
            addCriterion("c_mobile_phone not like", value, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneIn(List<String> values) {
            addCriterion("c_mobile_phone in", values, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneNotIn(List<String> values) {
            addCriterion("c_mobile_phone not in", values, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneBetween(String value1, String value2) {
            addCriterion("c_mobile_phone between", value1, value2, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCMPhoneNotBetween(String value1, String value2) {
            addCriterion("c_mobile_phone not between", value1, value2, "cMPhone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneIsNull() {
            addCriterion("c_telephone is null");
            return (Criteria) this;
        }

        public Criteria andCTelephoneIsNotNull() {
            addCriterion("c_telephone is not null");
            return (Criteria) this;
        }

        public Criteria andCTelephoneEqualTo(String value) {
            addCriterion("c_telephone =", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneNotEqualTo(String value) {
            addCriterion("c_telephone <>", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneGreaterThan(String value) {
            addCriterion("c_telephone >", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("c_telephone >=", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneLessThan(String value) {
            addCriterion("c_telephone <", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneLessThanOrEqualTo(String value) {
            addCriterion("c_telephone <=", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneLike(String value) {
            addCriterion("c_telephone like", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneNotLike(String value) {
            addCriterion("c_telephone not like", value, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneIn(List<String> values) {
            addCriterion("c_telephone in", values, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneNotIn(List<String> values) {
            addCriterion("c_telephone not in", values, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneBetween(String value1, String value2) {
            addCriterion("c_telephone between", value1, value2, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCTelephoneNotBetween(String value1, String value2) {
            addCriterion("c_telephone not between", value1, value2, "cTelephone");
            return (Criteria) this;
        }

        public Criteria andCEmailIsNull() {
            addCriterion("c_email is null");
            return (Criteria) this;
        }

        public Criteria andCEmailIsNotNull() {
            addCriterion("c_email is not null");
            return (Criteria) this;
        }

        public Criteria andCEmailEqualTo(String value) {
            addCriterion("c_email =", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailNotEqualTo(String value) {
            addCriterion("c_email <>", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailGreaterThan(String value) {
            addCriterion("c_email >", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailGreaterThanOrEqualTo(String value) {
            addCriterion("c_email >=", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailLessThan(String value) {
            addCriterion("c_email <", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailLessThanOrEqualTo(String value) {
            addCriterion("c_email <=", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailLike(String value) {
            addCriterion("c_email like", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailNotLike(String value) {
            addCriterion("c_email not like", value, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailIn(List<String> values) {
            addCriterion("c_email in", values, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailNotIn(List<String> values) {
            addCriterion("c_email not in", values, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailBetween(String value1, String value2) {
            addCriterion("c_email between", value1, value2, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCEmailNotBetween(String value1, String value2) {
            addCriterion("c_email not between", value1, value2, "cEmail");
            return (Criteria) this;
        }

        public Criteria andCQQIsNull() {
            addCriterion("c_qq is null");
            return (Criteria) this;
        }

        public Criteria andCQQIsNotNull() {
            addCriterion("c_qq is not null");
            return (Criteria) this;
        }

        public Criteria andCQQEqualTo(String value) {
            addCriterion("c_qq =", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQNotEqualTo(String value) {
            addCriterion("c_qq <>", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQGreaterThan(String value) {
            addCriterion("c_qq >", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQGreaterThanOrEqualTo(String value) {
            addCriterion("c_qq >=", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQLessThan(String value) {
            addCriterion("c_qq <", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQLessThanOrEqualTo(String value) {
            addCriterion("c_qq <=", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQLike(String value) {
            addCriterion("c_qq like", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQNotLike(String value) {
            addCriterion("c_qq not like", value, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQIn(List<String> values) {
            addCriterion("c_qq in", values, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQNotIn(List<String> values) {
            addCriterion("c_qq not in", values, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQBetween(String value1, String value2) {
            addCriterion("c_qq between", value1, value2, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCQQNotBetween(String value1, String value2) {
            addCriterion("c_qq not between", value1, value2, "cQQ");
            return (Criteria) this;
        }

        public Criteria andCoManIsNull() {
            addCriterion("contact_man is null");
            return (Criteria) this;
        }

        public Criteria andCoManIsNotNull() {
            addCriterion("contact_man is not null");
            return (Criteria) this;
        }

        public Criteria andCoManEqualTo(String value) {
            addCriterion("contact_man =", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManNotEqualTo(String value) {
            addCriterion("contact_man <>", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManGreaterThan(String value) {
            addCriterion("contact_man >", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManGreaterThanOrEqualTo(String value) {
            addCriterion("contact_man >=", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManLessThan(String value) {
            addCriterion("contact_man <", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManLessThanOrEqualTo(String value) {
            addCriterion("contact_man <=", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManLike(String value) {
            addCriterion("contact_man like", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManNotLike(String value) {
            addCriterion("contact_man not like", value, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManIn(List<String> values) {
            addCriterion("contact_man in", values, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManNotIn(List<String> values) {
            addCriterion("contact_man not in", values, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManBetween(String value1, String value2) {
            addCriterion("contact_man between", value1, value2, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoManNotBetween(String value1, String value2) {
            addCriterion("contact_man not between", value1, value2, "coMan");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneIsNull() {
            addCriterion("co_moblie_phone is null");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneIsNotNull() {
            addCriterion("co_moblie_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneEqualTo(String value) {
            addCriterion("co_moblie_phone =", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneNotEqualTo(String value) {
            addCriterion("co_moblie_phone <>", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneGreaterThan(String value) {
            addCriterion("co_moblie_phone >", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("co_moblie_phone >=", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneLessThan(String value) {
            addCriterion("co_moblie_phone <", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneLessThanOrEqualTo(String value) {
            addCriterion("co_moblie_phone <=", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneLike(String value) {
            addCriterion("co_moblie_phone like", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneNotLike(String value) {
            addCriterion("co_moblie_phone not like", value, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneIn(List<String> values) {
            addCriterion("co_moblie_phone in", values, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneNotIn(List<String> values) {
            addCriterion("co_moblie_phone not in", values, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneBetween(String value1, String value2) {
            addCriterion("co_moblie_phone between", value1, value2, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoMPhoneNotBetween(String value1, String value2) {
            addCriterion("co_moblie_phone not between", value1, value2, "coMPhone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneIsNull() {
            addCriterion("co_telephone is null");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneIsNotNull() {
            addCriterion("co_telephone is not null");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneEqualTo(String value) {
            addCriterion("co_telephone =", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneNotEqualTo(String value) {
            addCriterion("co_telephone <>", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneGreaterThan(String value) {
            addCriterion("co_telephone >", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneGreaterThanOrEqualTo(String value) {
            addCriterion("co_telephone >=", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneLessThan(String value) {
            addCriterion("co_telephone <", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneLessThanOrEqualTo(String value) {
            addCriterion("co_telephone <=", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneLike(String value) {
            addCriterion("co_telephone like", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneNotLike(String value) {
            addCriterion("co_telephone not like", value, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneIn(List<String> values) {
            addCriterion("co_telephone in", values, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneNotIn(List<String> values) {
            addCriterion("co_telephone not in", values, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneBetween(String value1, String value2) {
            addCriterion("co_telephone between", value1, value2, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoTelephoneNotBetween(String value1, String value2) {
            addCriterion("co_telephone not between", value1, value2, "coTelephone");
            return (Criteria) this;
        }

        public Criteria andCoEmailIsNull() {
            addCriterion("co_email is null");
            return (Criteria) this;
        }

        public Criteria andCoEmailIsNotNull() {
            addCriterion("co_email is not null");
            return (Criteria) this;
        }

        public Criteria andCoEmailEqualTo(String value) {
            addCriterion("co_email =", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailNotEqualTo(String value) {
            addCriterion("co_email <>", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailGreaterThan(String value) {
            addCriterion("co_email >", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailGreaterThanOrEqualTo(String value) {
            addCriterion("co_email >=", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailLessThan(String value) {
            addCriterion("co_email <", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailLessThanOrEqualTo(String value) {
            addCriterion("co_email <=", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailLike(String value) {
            addCriterion("co_email like", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailNotLike(String value) {
            addCriterion("co_email not like", value, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailIn(List<String> values) {
            addCriterion("co_email in", values, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailNotIn(List<String> values) {
            addCriterion("co_email not in", values, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailBetween(String value1, String value2) {
            addCriterion("co_email between", value1, value2, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoEmailNotBetween(String value1, String value2) {
            addCriterion("co_email not between", value1, value2, "coEmail");
            return (Criteria) this;
        }

        public Criteria andCoQQIsNull() {
            addCriterion("co_qq is null");
            return (Criteria) this;
        }

        public Criteria andCoQQIsNotNull() {
            addCriterion("co_qq is not null");
            return (Criteria) this;
        }

        public Criteria andCoQQEqualTo(String value) {
            addCriterion("co_qq =", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQNotEqualTo(String value) {
            addCriterion("co_qq <>", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQGreaterThan(String value) {
            addCriterion("co_qq >", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQGreaterThanOrEqualTo(String value) {
            addCriterion("co_qq >=", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQLessThan(String value) {
            addCriterion("co_qq <", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQLessThanOrEqualTo(String value) {
            addCriterion("co_qq <=", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQLike(String value) {
            addCriterion("co_qq like", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQNotLike(String value) {
            addCriterion("co_qq not like", value, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQIn(List<String> values) {
            addCriterion("co_qq in", values, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQNotIn(List<String> values) {
            addCriterion("co_qq not in", values, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQBetween(String value1, String value2) {
            addCriterion("co_qq between", value1, value2, "coQQ");
            return (Criteria) this;
        }

        public Criteria andCoQQNotBetween(String value1, String value2) {
            addCriterion("co_qq not between", value1, value2, "coQQ");
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