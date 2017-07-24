package ru.otus.dobrovolsky.dbService;

import org.hibernate.stat.SecondLevelCacheStatistics;
import org.hibernate.stat.Statistics;

import java.util.HashMap;
import java.util.Map;

public class CacheDescriptor {
    private static CacheDescriptor instance;

    private Statistics statistics;
    private SecondLevelCacheStatistics secondLevelCacheStatisticsUserDataSet;
    private SecondLevelCacheStatistics secondLevelCacheStatisticsPhoneDataSet;
    private SecondLevelCacheStatistics secondLevelCacheStatisticsAddressDataSet;
    private long queryCacheHitCount;
    private long secondLevelCacheMissCount;
    private long queryCacheMissCount;
    private long secondLevelCacheHitCount;
    private long secondLevelHitU;
    private long secondLevelMissU;
    private long secondLevelHitP;
    private long secondLevelMissP;
    private long secondLevelHitA;
    private long secondLevelMissA;
    private long queryCachePutCount;
    private long secondLevelCachePutCount;
    private long sessionOpenCount;
    private long sessionCloseCount;
    private String secondLevelCacheRegionNames;
    private long secondLevelPutCountU;
    private long secondLevelPutCountP;
    private long secondLevelPutCountA;
    private long secondLevelSizeU;
    private long secondLevelSizeP;
    private long secondLevelSizeA;
    private String queries;

    private CacheDescriptor(Statistics statistics) {
        this.statistics = statistics;

        secondLevelCacheStatisticsUserDataSet = statistics.getSecondLevelCacheStatistics("ru.otus.dobrovolsky.base.dataSets.UserDataSet");
        secondLevelCacheStatisticsPhoneDataSet = statistics.getSecondLevelCacheStatistics("ru.otus.dobrovolsky.base.dataSets.PhoneDataSet");
        secondLevelCacheStatisticsAddressDataSet = statistics.getSecondLevelCacheStatistics("ru.otus.dobrovolsky.base.dataSets.AddressDataSet");
    }

    static CacheDescriptor getInstance(Statistics statistics) {
        if (instance == null) {
            instance = new CacheDescriptor(statistics);
        }
        return instance;
    }

    private long getQueryCacheHitCount() {

        return queryCacheHitCount;
    }

    private void setQueryCacheHitCount(long queryCacheHitCount) {
        this.queryCacheHitCount = queryCacheHitCount;
    }

    private long getSecondLevelCacheMissCount() {
        return secondLevelCacheMissCount;
    }

    private void setSecondLevelCacheMissCount(long secondLevelCacheMissCount) {
        this.secondLevelCacheMissCount = secondLevelCacheMissCount;
    }

    private long getQueryCacheMissCount() {
        return queryCacheMissCount;
    }

    private void setQueryCacheMissCount(long queryCacheMissCount) {
        this.queryCacheMissCount = queryCacheMissCount;
    }

    private long getSecondLevelCacheHitCount() {
        return secondLevelCacheHitCount;
    }

    private void setSecondLevelCacheHitCount(long secondLevelCacheHitCount) {
        this.secondLevelCacheHitCount = secondLevelCacheHitCount;
    }

    private long getSecondLevelHitU() {
        return secondLevelHitU;
    }

    private void setSecondLevelHitU(long secondLevelHitU) {
        this.secondLevelHitU = secondLevelHitU;
    }

    private long getSecondLevelMissU() {
        return secondLevelMissU;
    }

    private void setSecondLevelMissU(long secondLevelMissU) {
        this.secondLevelMissU = secondLevelMissU;
    }

    private long getSecondLevelHitP() {
        return secondLevelHitP;
    }

    private void setSecondLevelHitP(long secondLevelHitP) {
        this.secondLevelHitP = secondLevelHitP;
    }

    private long getSecondLevelMissP() {
        return secondLevelMissP;
    }

    private void setSecondLevelMissP(long secondLevelMissP) {
        this.secondLevelMissP = secondLevelMissP;
    }

    private long getSecondLevelHitA() {
        return secondLevelHitA;
    }

    private void setSecondLevelHitA(long secondLevelHitA) {
        this.secondLevelHitA = secondLevelHitA;
    }

    private long getSecondLevelMissA() {
        return secondLevelMissA;
    }

    private void setSecondLevelMissA(long secondLevelMissA) {
        this.secondLevelMissA = secondLevelMissA;
    }

    private long getQueryCachePutCount() {
        return queryCachePutCount;
    }

    private void setQueryCachePutCount(long queryCachePutCount) {
        this.queryCachePutCount = queryCachePutCount;
    }

    private long getSecondLevelCachePutCount() {
        return secondLevelCachePutCount;
    }

    private void setSecondLevelCachePutCount(long secondLevelCachePutCount) {
        this.secondLevelCachePutCount = secondLevelCachePutCount;
    }

    private long getSessionOpenCount() {
        return sessionOpenCount;
    }

    private void setSessionOpenCount(long sessionOpenCount) {
        this.sessionOpenCount = sessionOpenCount;
    }

    private long getSessionCloseCount() {
        return sessionCloseCount;
    }

    private void setSessionCloseCount(long sessionCloseCount) {
        this.sessionCloseCount = sessionCloseCount;
    }

    private String getSecondLevelCacheRegionNames() {
        return secondLevelCacheRegionNames;
    }

    private void setSecondLevelCacheRegionNames(Statistics statistics) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : statistics.getSecondLevelCacheRegionNames()) {
            stringBuilder.append("{").append(s).append("}").append("\n");
        }
        this.secondLevelCacheRegionNames = stringBuilder.toString();
    }

    private long getSecondLevelPutCountU() {
        return secondLevelPutCountU;
    }

    private void setSecondLevelPutCountU(long secondLevelPutCountU) {
        this.secondLevelPutCountU = secondLevelPutCountU;
    }

    private long getSecondLevelPutCountP() {
        return secondLevelPutCountP;
    }

    private void setSecondLevelPutCountP(long secondLevelPutCountP) {
        this.secondLevelPutCountP = secondLevelPutCountP;
    }

    private long getSecondLevelPutCountA() {
        return secondLevelPutCountA;
    }

    private void setSecondLevelPutCountA(long secondLevelPutCountA) {
        this.secondLevelPutCountA = secondLevelPutCountA;
    }

    private long getSecondLevelSizeU() {
        return secondLevelSizeU;
    }

    private void setSecondLevelSizeU(long secondLevelSizeU) {
        this.secondLevelSizeU = secondLevelSizeU;
    }

    private long getSecondLevelSizeP() {
        return secondLevelSizeP;
    }

    private void setSecondLevelSizeP(long secondLevelSizeP) {
        this.secondLevelSizeP = secondLevelSizeP;
    }

    private long getSecondLevelSizeA() {
        return secondLevelSizeA;
    }

    private void setSecondLevelSizeA(long secondLevelSizeA) {
        this.secondLevelSizeA = secondLevelSizeA;
    }

    private String getQueries() {
        return queries;
    }

    private void setQueries(Statistics statistics) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String q : statistics.getQueries()) {
            stringBuilder.append("{").append(q).append("}").append("\n");
        }

        this.queries = stringBuilder.toString();
    }

    public void updateFields() {
        setQueryCacheHitCount(statistics.getQueryCacheHitCount());
        setSecondLevelCacheMissCount(statistics.getSecondLevelCacheMissCount());
        setQueryCacheMissCount(statistics.getQueryCacheMissCount());
        setSecondLevelCacheHitCount(statistics.getSecondLevelCacheHitCount());
        setSecondLevelHitU(secondLevelCacheStatisticsUserDataSet.getHitCount());
        setSecondLevelMissU(secondLevelCacheStatisticsUserDataSet.getMissCount());
        setSecondLevelHitP(secondLevelCacheStatisticsPhoneDataSet.getHitCount());
        setSecondLevelMissP(secondLevelCacheStatisticsPhoneDataSet.getMissCount());
        setSecondLevelHitA(secondLevelCacheStatisticsAddressDataSet.getHitCount());
        setSecondLevelMissA(secondLevelCacheStatisticsAddressDataSet.getMissCount());
        setQueryCachePutCount(statistics.getQueryCachePutCount());
        setSecondLevelCachePutCount(statistics.getSecondLevelCachePutCount());
        setSessionOpenCount(statistics.getSessionOpenCount());
        setSessionCloseCount(statistics.getSessionCloseCount());
        setSecondLevelCacheRegionNames(statistics);
        setSecondLevelPutCountU(secondLevelCacheStatisticsUserDataSet.getPutCount());
        setSecondLevelPutCountP(secondLevelCacheStatisticsPhoneDataSet.getPutCount());
        setSecondLevelPutCountA(secondLevelCacheStatisticsAddressDataSet.getPutCount());
        setSecondLevelSizeU(secondLevelCacheStatisticsUserDataSet.getSizeInMemory());
        setSecondLevelSizeP(secondLevelCacheStatisticsPhoneDataSet.getSizeInMemory());
        setSecondLevelSizeA(secondLevelCacheStatisticsAddressDataSet.getSizeInMemory());
        setQueries(statistics);
    }

    public Map<String, Object> getCacheMap() {
        updateFields();

        Map<String, Object> cacheMap = new HashMap<>();

        cacheMap.put("queryCacheHitCount", this.getQueryCacheHitCount());
        cacheMap.put("queryCacheMissCount", this.getQueryCacheMissCount());
        cacheMap.put("secondLevelCacheHitCount", this.getSecondLevelCacheHitCount());
        cacheMap.put("secondLevelCacheMissCount", this.getSecondLevelCacheMissCount());
        cacheMap.put("secondLevelHitU", this.getSecondLevelHitU());
        cacheMap.put("secondLevelMissU", this.getSecondLevelMissU());
        cacheMap.put("secondLevelHitP", this.getSecondLevelHitP());
        cacheMap.put("secondLevelMissP", this.getSecondLevelMissP());
        cacheMap.put("secondLevelHitA", this.getSecondLevelHitA());
        cacheMap.put("secondLevelMissA", this.getSecondLevelMissA());
        cacheMap.put("queries", this.getQueries());
        cacheMap.put("queryCachePutCount", this.getQueryCachePutCount());
        cacheMap.put("secondLevelCachePutCount", this.getSecondLevelCachePutCount());
        cacheMap.put("sessionOpenCount", this.getSessionOpenCount());
        cacheMap.put("sessionCloseCount", this.getSessionCloseCount());
        cacheMap.put("secondLevelPutCountU", this.getSecondLevelPutCountU());
        cacheMap.put("secondLevelSizeU", this.getSecondLevelSizeU());
        cacheMap.put("secondLevelPutCountP", this.getSecondLevelPutCountP());
        cacheMap.put("secondLevelSizeP", this.getSecondLevelSizeP());
        cacheMap.put("secondLevelPutCountA", this.getSecondLevelPutCountA());
        cacheMap.put("secondLevelSizeA", this.getSecondLevelSizeA());
        cacheMap.put("secondLevelCacheRegionNames", this.getSecondLevelCacheRegionNames());

        return cacheMap;
    }
}
