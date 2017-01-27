package ru.roborox.itunesconnect.api.reporting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.roborox.itunesconnect.api.common.Sort;
import ru.roborox.itunesconnect.api.reporting.model.enums.Dimension;
import ru.roborox.itunesconnect.api.reporting.model.enums.Interval;
import ru.roborox.itunesconnect.api.reporting.model.enums.ReportingMeasure;

import java.util.Arrays;
import java.util.Date;

public class TimeSeriesRequest {
    private Interval interval;
    private Dimension group;
    private Date startDate;
    private Date endDate;
    private Sort sort;
    private int limit;
    private ReportingMeasure[] measures;
    private TimeSeriesFilter[] filters;

    public TimeSeriesRequest(Interval interval, Dimension group, Date startDate, Date endDate, Sort sort, int limit, ReportingMeasure[] measures, TimeSeriesFilter[] filters) {
        this.interval = interval;
        this.group = group;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sort = sort;
        this.limit = limit;
        this.measures = measures;
        this.filters = filters;
    }

    public TimeSeriesRequest(Interval interval, Dimension group, Date startDate, Date endDate, Sort sort, int limit, TimeSeriesFilter filter, ReportingMeasure... measures) {
        this(interval, group, startDate, endDate, sort, limit, measures, new TimeSeriesFilter[]{filter});
    }

    public Dimension getGroup() {
        return group;
    }

    public void setGroup(Dimension group) {
        this.group = group;
    }

    public TimeSeriesFilter[] getFilters() {
        return filters;
    }

    public void setFilters(TimeSeriesFilter[] filters) {
        this.filters = filters;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    @JsonProperty("start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public ReportingMeasure[] getMeasures() {
        return measures;
    }

    public void setMeasures(ReportingMeasure[] measures) {
        this.measures = measures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeSeriesRequest that = (TimeSeriesRequest) o;

        if (limit != that.limit) return false;
        if (interval != that.interval) return false;
        if (group != that.group) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (sort != that.sort) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(measures, that.measures)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(filters, that.filters);
    }

    @Override
    public int hashCode() {
        int result = interval != null ? interval.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + limit;
        result = 31 * result + Arrays.hashCode(measures);
        result = 31 * result + Arrays.hashCode(filters);
        return result;
    }
}