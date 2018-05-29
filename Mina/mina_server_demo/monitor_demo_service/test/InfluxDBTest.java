import com.influx.InfluxdbService;
import com.influx.pojo.NodeInfo;
import com.influx.pojo.NodeInfoTest;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.*;

public class InfluxDBTest {

    private InfluxdbService influxDB;

    private String username = "admin";//用户名
    private String password = "admin";//密码
    private String openurl = "http://192.161.14.102:8086";
    private String database = "mina_test";
    private String mearsurement = "mina";


    @Before
    public void setUp(){
        /*创建连接*/
        influxDB = new InfluxdbService(username,password,openurl,database);
        influxDB.influxDBBuild();
        influxDB.createRetentionPolicy("defalut", database, "30d", 1);
    }

    @Test
    public void testInsert(){
        Map<String,String> tags = new HashMap<>();
        Map<String,Object> fields = new HashMap<>();
        List<NodeInfoTest> list = new ArrayList<>();



        for (int i = 0; i < 1000; i++) {
            NodeInfoTest nodeInfo = new NodeInfoTest();
            Random random = new Random();
            nodeInfo.setNodeId(String.valueOf(i));
            nodeInfo.setNodeName("Node_"+i);
            nodeInfo.setNodeType(i%2);
            nodeInfo.setContinueSucessCount(random.nextInt(10000));
            list.add(nodeInfo);
        }

        for (NodeInfoTest info:list){
            tags.put("nodeId",info.getNodeId());

            fields.put("nodeName",info.getNodeName());
            fields.put("nodeType",info.getNodeType());
            fields.put("continueSucessCount",info.getContinueSucessCount());

            influxDB.insert(mearsurement,tags,fields);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void dropDatabases(){
        influxDB.deleteDB("mina_test");
    }

}
