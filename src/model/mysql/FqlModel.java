package model.mysql;

import model.mysql.utility.FqlStringBuilder;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FqlModel<T> {

    String table;
    String url;

    public FqlModel() {
    }

    public FqlModel(String table, String url) {
        this.table = table;
        this.url = url;
    }

    /**
     * @param t
     * @return object t1, the new object get from database
     * @throws IllegalAccessException
     */
    public T getOne(T t) throws SQLException, IllegalAccessException, InstantiationException {
        T t1 = null;

        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        t1 = (T) t.getClass().newInstance();

        ResultSet rs = stm.executeQuery(new FqlStringBuilder().select(this.table).where(t).limit(1).build());

        Field[] aField = t.getClass().getDeclaredFields();
        while (rs.next()) {
            for (Field f : aField) {
                f.setAccessible(true);
                f.set(t1, rs.getObject(f.getName(), f.getType()));
            }
        }

        rs.close();
        stm.close();
        con.close();
        return t1;
    }

    /**
     * @param t
     * @return boolean, true if query success, false if failed
     * @catch SQLException
     * @catch IllegalAccessException
     */
    public boolean insertOne(T t) throws SQLException, IllegalAccessException {
        boolean af = false;

        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        af = stm.execute(new FqlStringBuilder().insert(this.table).addInsertItem(t).build());
        con.close();

        return af;
    }

    /**
     * @param t
     * @return boolean, true if success, false if failed
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public boolean updateOne(T t) throws SQLException, IllegalAccessException {
        boolean af = false;

        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        af = stm.execute(new FqlStringBuilder().update(this.table).setItemUpdate(t).build());
        stm.close();
        con.close();


        return af;
    }

    /**
     * @param query
     * @return get one object with the query in Map data type, return null if catch any exception
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public T getOne(Map<String, Object> query, Class<T> cl) throws SQLException, IllegalAccessException, InstantiationException {
        T obj = null;

        obj = cl.newInstance();
        Field[] fs = obj.getClass().getDeclaredFields();
        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(new FqlStringBuilder().select(this.table).where(query).limit(1).build());

        while (rs.next()) {
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);
                fs[i].set(obj, rs.getObject(fs[i].getName(), fs[i].getType()));
            }
        }
        rs.close();
        stm.close();
        con.close();

        return obj;
    }

    /**
     * @param
     * @return get the list of record
     * @throws SQLException
     * @throws IllegalAccessException
     * @option get by class (get all), get with an object and get by query
     */
    public List<T> getList(Class<T> cl) throws IllegalAccessException, SQLException, InstantiationException {
        return getList(null, 10, 1, cl);
    }

    public List<T> getList(T t) throws IllegalAccessException, SQLException, InstantiationException {
        return getList(t, 10, 1);
    }

    public List<T> getList(T t, int limit, int offset) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();

        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(new FqlStringBuilder().select(this.table).where(t).limit(limit).offset(offset).build());

        T obj;
        Field[] fs = t.getClass().getDeclaredFields();
        while (rs.next()) {
            obj = (T) t.getClass().newInstance();
            for (Field f : fs) {
                f.setAccessible(true);
                f.set(obj, rs.getObject(f.getName(), f.getType()));
            }
            list.add(obj);
        }

        rs.close();
        stm.close();
        con.close();

        return list;
    }

    public List<T> getList(Map<String, Object> query, int limit, int offset, Class<T> cl) throws SQLException, IllegalAccessException, InstantiationException {
        List<T> list = new ArrayList<>();
        Connection con = DriverManager.getConnection(this.url);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(new FqlStringBuilder().select(this.table).where(query).limit(limit).offset(offset).build());

        T obj;
        Field[] fs = cl.getDeclaredFields();
        while (rs.next()) {
            obj = cl.newInstance();
            for (int i = 0; i < fs.length; i++) {
                fs[i].setAccessible(true);
                fs[i].set(obj, rs.getObject(fs[i].getName(), fs[i].getType()));
            }
            list.add(obj);
        }

        rs.close();
        stm.close();
        con.close();

        return list;
    }
}
