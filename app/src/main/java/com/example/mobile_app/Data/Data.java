//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.MongoException;
//import com.mongodb.ServerApi;
//import com.mongodb.ServerApiVersion;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;
//import org.bson.Document;
//
//public class Data {
//    public Data() {
//    }
//
//    public static void data(String[] args) {
//        String connectionString = "mongodb+srv://gn27082004:Giang27082004@mongodb.c6fozoj.mongodb.net/";
//        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
//        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
//        MongoClient mongoClient = MongoClients.create(settings);
//
//        try {
//            try {
//                MongoDatabase database = mongoClient.getDatabase("admin");
//                database.runCommand(new Document("ping", 1));
//                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
//            } catch (MongoException var8) {
//                var8.printStackTrace();
//            }
//        } catch (Throwable var9) {
//            if (mongoClient != null) {
//                try {
//                    mongoClient.close();
//                } catch (Throwable var7) {
//                    var9.addSuppressed(var7);
//                }
//            }
//
//            throw var9;
//        }
//
//        if (mongoClient != null) {
//            mongoClient.close();
//        }
//
//    }
//}
