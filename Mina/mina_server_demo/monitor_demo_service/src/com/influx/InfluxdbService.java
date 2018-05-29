package com.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InfluxdbService {

    private String username;//用户名
    private String password;//密码
    private String openurl;//连接地址
    private String database;//数据库

    private InfluxDB influxDB;

    public InfluxdbService(String username, String password, String openurl, String database) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
    }

    /**
     * 连接时序数据库;获得Influxdb
     * @return influxDB
     */
    public InfluxDB influxDBBuild(){
        if (influxDB == null){
            influxDB = InfluxDBFactory.connect(openurl,username,password);
            influxDB.createDatabase(database);
        }
        return influxDB;
    }

    /**
     * 创建保存策略
     * @param database 数据库名称
     * @param policyName 策略名称
     * @param duration 持续时间,0代表无限制
     * @param replicationNum 副本个数
     */
    public void createRetentionPolicy(String database, String policyName,String duration, int replicationNum){
        String commond = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",policyName,database,duration,replicationNum);
        this.query(database,commond);
    }

    /**
     * 插入数据
     * @param measurement 表
     * @param tags 标签
     * @param fields 字段
     */
    public void insert(String measurement, Map<String,String> tags, Map<String,Object> fields){
        insert(System.currentTimeMillis(),TimeUnit.MILLISECONDS,measurement,tags,fields);
    }

    /**
     * 用户内部接口调用插入数据
     * @param timestamp
     * @param timeUnit
     * @param measurement 表
     * @param tags 标签
     * @param fields 字段
     */
    public void insert(long timestamp, TimeUnit timeUnit, String measurement, Map<String, String> tags,Map<String, Object> fields){
        Point.Builder builder = Point.measurement(measurement);

        builder.tag(tags);
        builder.fields(fields);

        influxDB.write(database,"",builder.build());

    }

    private QueryResult query(String database, String commond) {
        return influxDB.query(new Query(commond,database));
    }

    /**
     * 删除数据库
     * @param dbName 数据库名称
     */
    public void deleteDB(String dbName){
        influxDB.deleteDatabase(dbName);
    }

    /**
     * 创建数据库
     * @param database 数据库名称
     */
    public void createDatabase(String database){
        influxDB.createDatabase(database);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenurl() {
        return openurl;
    }

    public void setOpenurl(String openurl) {
        this.openurl = openurl;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
