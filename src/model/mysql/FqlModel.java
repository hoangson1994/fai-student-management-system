package model.mysql;

import com.google.protos.cloud.sql.Client;
import model.mysql.utility.FqlStringBuilder;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FqlModel <T> {

    private String table;
    private String url;

    public FqlModel() {
    }

    FqlModel(String table, String url) {
        this.table = table;
        this.url = url;
    }

    /**
     *
     * @param t
     * @return object t1, the new object get from database
     * @throws IllegalAccessException
     */
    public T getOne(T t) throws IllegalAccessException {
        T t1 = (T) new Object();

        Field[] aField = t.getClass().getDeclaredFields();

        for (Field f: aField) {
            f.setAccessible(true);
            if (f.get(t) == null) continue;

            System.out.println("Add condition query to sql string");
        }

        return t1;
    }

    /**
     *
     * @param t
     * @return boolean, true if query success, false if failed
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public boolean insertOne(T t) throws SQLException, IllegalAccessException {
//        Connection con = DriverManager.getConnection("jdbc:mysql://35.224.127.210/fpt_university?user=focus2&password=hoang123&useSSL=false");
        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        //System.out.println(new FqlStringBuilder().update(this.table).setItemUpdate(t).whereAnd("rollNumber", "A002").build());
        boolean af = stm.execute(new FqlStringBuilder().insert(this.table).addInsertItem(t).build());
        con.close();

        return true;
    }

    /**
     *
     * @param t
     * @return boolean, true if success, false if failed
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public boolean updateOne(T t) throws SQLException, IllegalAccessException {

        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        boolean af = stm.execute(new FqlStringBuilder().update(this.table).setItemUpdate(t).build());
        stm.close();
        con.close();

        return af;
    }

    /**
     *
     * @param query
     * @return get one object with the query in Map data type
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public T getOne(Map<String, Object> query) throws SQLException, IllegalAccessException {
        T obj = (T) new Object();
        Field[] fs = obj.getClass().getDeclaredFields();
        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(new FqlStringBuilder().select(this.table).where(query).limit(1).build());

        while (rs.next()) {
            for (int i=0; i<fs.length; i++) {
                fs[i].setAccessible(true);
                fs[i].set(obj, rs.getObject(fs[i].getName()));
            }
        }
        rs.close();
        stm.close();
        con.close();
        return obj;
    }

    /**
     *
     * @param query
     * @return get the list of record
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public List<T> getList(Map<String, Object> query) throws SQLException, IllegalAccessException {
        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(new FqlStringBuilder().select(this.table).where(query).build());
        List<T> list = new ArrayList<>();
        T obj = (T) new Object();
        Field[] fs = obj.getClass().getDeclaredFields();
        while (rs.next()) {
            obj = (T) new Object();
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);
                fs[i].set(obj, rs.getObject(fs[i].getName()));
            }
            list.add(obj);
        }

        rs.close();
        stm.close();
        con.close();

        return list;
    }
}
