package com.kingguanzhang.dealsites.pojo;

import java.util.ArrayList;
import java.util.List;

public class FavoriteShopExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FavoriteShopExample() {
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

        public Criteria andFavoriteShopIdIsNull() {
            addCriterion("favorite_shop_id is null");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdIsNotNull() {
            addCriterion("favorite_shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdEqualTo(Integer value) {
            addCriterion("favorite_shop_id =", value, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdNotEqualTo(Integer value) {
            addCriterion("favorite_shop_id <>", value, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdGreaterThan(Integer value) {
            addCriterion("favorite_shop_id >", value, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("favorite_shop_id >=", value, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdLessThan(Integer value) {
            addCriterion("favorite_shop_id <", value, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("favorite_shop_id <=", value, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdIn(List<Integer> values) {
            addCriterion("favorite_shop_id in", values, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdNotIn(List<Integer> values) {
            addCriterion("favorite_shop_id not in", values, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdBetween(Integer value1, Integer value2) {
            addCriterion("favorite_shop_id between", value1, value2, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andFavoriteShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("favorite_shop_id not between", value1, value2, "favoriteShopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Integer value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Integer value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Integer value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Integer value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Integer> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Integer> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Integer value1, Integer value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdIsNull() {
            addCriterion("personInfo_id is null");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdIsNotNull() {
            addCriterion("personInfo_id is not null");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdEqualTo(Integer value) {
            addCriterion("personInfo_id =", value, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdNotEqualTo(Integer value) {
            addCriterion("personInfo_id <>", value, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdGreaterThan(Integer value) {
            addCriterion("personInfo_id >", value, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("personInfo_id >=", value, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdLessThan(Integer value) {
            addCriterion("personInfo_id <", value, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdLessThanOrEqualTo(Integer value) {
            addCriterion("personInfo_id <=", value, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdIn(List<Integer> values) {
            addCriterion("personInfo_id in", values, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdNotIn(List<Integer> values) {
            addCriterion("personInfo_id not in", values, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdBetween(Integer value1, Integer value2) {
            addCriterion("personInfo_id between", value1, value2, "personinfoId");
            return (Criteria) this;
        }

        public Criteria andPersoninfoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("personInfo_id not between", value1, value2, "personinfoId");
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