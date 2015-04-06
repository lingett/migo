package mongo;

import com.google.common.collect.Maps;
import com.mongodb.*;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import utils.JsonUtils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

public class MongoOperate {
    private Mongo mongo;

    private final static String DB = "MovieFans";
    private final static String MONGO_URL = "192.168.213.118:27017";
    private final static String MOVIE_FANS_BEHAVIOR_TRACE_LOG = "MovieFansBehaviorTraceLog";
    private final DBObject INDEX = new BasicDBObject("userId", 1).append("typeId", 1).append("subjectId", 1).append("status", 1).append("addTime", 1);

    // 稀疏索引
    private final DBObject UNIQUE_INDEX = new BasicDBObject("unique", true).append("sparse", true);

    public void init() {
        try {
            List<ServerAddress> replicaSetSeeds = parseUri(MONGO_URL);
            mongo = new Mongo(replicaSetSeeds, getDefaultOptions());
            DBCollection movieFansBehaviorTraceLog = mongo.getDB(DB).getCollection(MOVIE_FANS_BEHAVIOR_TRACE_LOG);
            movieFansBehaviorTraceLog.ensureIndex(INDEX, UNIQUE_INDEX);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<ServerAddress> parseUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            return null;
        }
        String[] hostPortArr = uri.split(",");
        List<ServerAddress> result = new ArrayList<ServerAddress>();
        for (int i = 0; i < hostPortArr.length; i++) {
            String[] pair = hostPortArr[i].split(":");
            try {
                result.add(new ServerAddress(pair[0].trim(), Integer.parseInt(pair[1].trim())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private MongoOptions getDefaultOptions() {
        MongoOptions options = new MongoOptions();
        options.slaveOk = false;
        options.socketKeepAlive = false;
        options.socketTimeout = 5000;
        options.connectionsPerHost = 30;
        options.threadsAllowedToBlockForConnectionMultiplier = 50;
        options.w = 0;
        options.wtimeout = 5000;
        options.fsync = false;
        options.connectTimeout = 5000;
        options.maxWaitTime = 1000 * 60 * 2;
        options.autoConnectRetry = false;
        options.safe = false;
        return options;
    }

    public void insert(Object object, String collectionName) {
        try {
            Map<String, Object> map = parseClass(object);
            DBCollection collection = mongo.getDB(DB).getCollection(collectionName);
            BasicDBObject dbObject = new BasicDBObject(map);
            collection.insert(dbObject, WriteConcern.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> select(Map<String, Object> clauseMap, Class<T> clazz, String collectionName) {
        DBCollection collection = mongo.getDB(DB).getCollection(collectionName);
        BasicDBObject clause = new BasicDBObject(clauseMap);
        DBCursor curObject = collection.find(clause);
        if (curObject.count() < 1) {
            return null;
        }
        List<DBObject> array = curObject.toArray();
        if (array == null || array.size() < 1) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (DBObject object : array) {
            result.add(parseMap((Map<String, Object>) object.toMap(), clazz));
        }
        return result;
    }

    public static Map<String, Object> parseClass(Object object) {
        Map<String, Object> result = new HashMap<String, Object>();
        Method[] declaredMethods = object.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                try {
                    result.put(getFieldNameFromGetMethod(methodName), method.invoke(object));
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return result;
    }

    public static <T> T parseMap(Map<String, Object> map, Class<T> clazz) {
        try {
            T instance = clazz.newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String methodName = getMethodNameFromFieldName(entry.getKey());
                try {
                    Class<?> methodParamType = entry.getValue().getClass();
                    if (methodParamType == ObjectId.class) {
                        continue;
                    } else if (methodParamType == BasicDBList.class) {
                        methodParamType = List.class;
                    }
                    clazz.getMethod(methodName, methodParamType).invoke(instance, entry.getValue());
                } catch (Exception e) {
                    continue;
                }
            }
            return instance;
        } catch (Exception e) {
            return null;
        }
    }

    private static String getFieldNameFromGetMethod(String methodName) {
        String name = methodName.substring("get".length());
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    private static String getMethodNameFromFieldName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public void insert() {
        String content = "{\"userId\": 14562924,\"typeId\":4,\"subjectId\":10102,\"addTime\":\"2015-02-10 11:52:04\"}";
//        String content = "{\"userId\": 14562924,\"typeId\":5,\"subjectId\":161,\"addTime\":\"2015-02-10 11:52:04\"}";
        MovieFansBehaviorMqDto dto = new MovieFansBehaviorMqDto();
        dto.setUserId(14562924);
        dto.setTypeId(4);
        dto.setSubjectId(10102);
        dto.setContent(content);

        insert(dto, MOVIE_FANS_BEHAVIOR_TRACE_LOG);
    }

    public List<MovieFansBehaviorMqDto> select() {
        Map<String, Object> clauseMap = Maps.newHashMap();
        clauseMap.put("userId", 14562924);
        return select(clauseMap, MovieFansBehaviorMqDto.class, MOVIE_FANS_BEHAVIOR_TRACE_LOG);
    }

    public static void main(String... args) {
        MongoOperate operate = new MongoOperate();
        operate.init();

//        operate.insert();
        List<MovieFansBehaviorMqDto> result = operate.select();
        for(MovieFansBehaviorMqDto dto : result) {
            System.out.println(dto);
        }
    }
}
