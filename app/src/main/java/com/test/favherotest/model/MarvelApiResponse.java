package com.test.favherotest.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by comac on 19/02/2017.
 */


public class MarvelApiResponse<PayLoadType> {
    private String code;
    private String status;
    private Data<PayLoadType> data;

    public class Data<PayLoadType> {
        private Integer offset;
        private Integer limit;
        private Integer total;
        private Integer count;
        private List<PayLoadType> results;

        public Integer getOffset() {
            return offset;
        }

        public Integer getLimit() {
            return limit;
        }

        public Integer getTotal() {
            return total;
        }

        public Integer getCount() {
            return count;
        }

        public List<PayLoadType> getResults() {
            return results;
        }

        public Data<PayLoadType> empty() {
            offset = 0;
            limit = 0;
            total = 0;
            count = 0;
            results = new ArrayList<>();
            return this;
        }

        public Data<PayLoadType> withValues(List<PayLoadType> values) {
            offset = 0;
            limit = 0;
            total = values.size();
            count = values.size();
            results = values;
            return this;
        }
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public Data<PayLoadType> getData() {
        return data;
    }

    public MarvelApiResponse<PayLoadType> empty() {
        code = "";
        status = "";
        data = new Data<PayLoadType>().empty();
        return this;
    }

    public MarvelApiResponse<PayLoadType> withValues(List<PayLoadType> values) {
        code = "";
        status = "";
        data = new Data<PayLoadType>().withValues(values);
        return this;
    }
}
