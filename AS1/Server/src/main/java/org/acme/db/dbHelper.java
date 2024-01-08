package org.acme.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class dbHelper {
    public static MongoClient connectMongoDB(){
        return MongoClients.create();
    }

    public static MongoClient connectMongoDB(String ip, int port){
        return MongoClients.create("mongodb://" + ip + ":" + port);
    }
}
