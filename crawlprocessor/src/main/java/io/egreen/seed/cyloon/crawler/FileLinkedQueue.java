package io.egreen.seed.cyloon.crawler;

import com.sleepycat.je.*;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dewmal on 12/13/15.
 */
public class FileLinkedQueue extends LinkedBlockingQueue {

    private Database database;
    private Environment env;
    private String dbName;

    private boolean ok = false;

    public FileLinkedQueue(String dbName) {
        this.dbName = dbName;
        try {
            System.out.println(dbName);
            EnvironmentConfig environmentConfig = new EnvironmentConfig();
            environmentConfig.setTransactional(true);
            environmentConfig.setAllowCreate(true);
            File file = new File("/home/dewmal/cyloon_crawler_db/" + dbName);
            if (!file.exists()) {
                file.mkdir();
            }
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setTransactional(true);
            dbConfig.setAllowCreate(true);
            env = new Environment(file, environmentConfig);
            database = env.openDatabase(null, dbName, dbConfig);
//        seedlinks = new StoredList<String>(database, new StringBinding(), true);
            System.out.println("Crawler Done");

            Transaction transaction = env.beginTransaction(null, null);
            Cursor cursor = database.openCursor(transaction, null);
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry value = new DatabaseEntry();
            OperationStatus result = cursor.getFirst(key, value, null);
            while ((result == OperationStatus.SUCCESS)) {
                if (value.getData().length > 0) {
                    add(new String(value.getData()));
                }
                result = cursor.getNext(key, value, null);
            }
            cursor.close();
            transaction.commit();
            ok = true;

            System.out.println(" Begin  DB " + dbName + " - " + size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Object take() throws InterruptedException {
        Object take = super.take();


        try {
            System.out.println(take);
            synchronized (database) {
                Transaction transaction = env.beginTransaction(null, null);
                Cursor cursor = database.openCursor(transaction, null);
                DatabaseEntry key = new DatabaseEntry((take + "").getBytes());
                DatabaseEntry value = new DatabaseEntry();
                OperationStatus result = cursor.getSearchKey(key, value, null);

                if (result == OperationStatus.SUCCESS) {
                    result = cursor.delete();
                }
                cursor.close();
                transaction.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return take;
    }

    @Override
    public boolean add(Object o) {
//        synchronized (database) {
        System.out.println(o);
//tr
        if (ok) {
            try {

                synchronized (database) {
                    Transaction transaction = env.beginTransaction(null,null);
                    DatabaseEntry key = new DatabaseEntry((o + "").getBytes());
                    DatabaseEntry data = new DatabaseEntry((o + "").getBytes());
                    database.put(transaction, key, data);
                    transaction.commit();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.add(o);
    }
}
