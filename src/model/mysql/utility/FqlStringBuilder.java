package model.mysql.utility;

import model.mysql.annotation.Id;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class FqlStringBuilder {
//    // 1 - 2 - 3 - 4 => crud
//    private int type;
//    // List item để update, delete, find, create
//    private List<T> items;
//    // table of query
//    private String table;
//    // condition of query. Trong mỗi Map, mỗi phần là 1 điều kiện, nối nhau bằng từ khóa AND, và giữa mỗi map nối nhau bằng từ khóa OR
//    // Value của mỗi giá trị trong map không phải là giá trị thực, mà là biểu thức giữa key và giá trị VD: =, >, <, IN,...
//    private List<Map<String, String>> query;
//    // the key is the field to order, and value is type of order true ASC, false is DESC
//    private Map<String, Boolean> orderBy;
//    // true is take count of query, false isn't
//    private boolean count;
//    // the properties to set in row of sql
//    private Map<String, Object> propertiesSet;
//
//    public FqlStringBuilder setType(int type) {
//        this.type = type;
//        return this;
//    }
//
//    public FqlStringBuilder setTable(String table) {
//        this.table = table;
//        return this;
//    }
//
//    public FqlStringBuilder setQuery(List<Map<String, String>> query) {
//        this.query = query;
//        return this;
//    }
//
//    public FqlStringBuilder addQuery(String key, String value) {
//        return addQuery(key, value, 0);
//    }
//
//    public FqlStringBuilder setItem(List<T> items) {
//        this.items = items;
//        return this;
//    }
//
//    public FqlStringBuilder addItem(T item) {
//        if (items == null) items = new ArrayList<>();
//        items.add(item);
//        return this;
//    }
//
//    public FqlStringBuilder addQuery(String key, String value, int indexOfQuery) {
//        if (query == null) query = new ArrayList<>();
//        if (indexOfQuery > query.size()) throw new IndexOutOfBoundsException("The index of query too big from size if query list");
//        if (indexOfQuery == query.size()) query.add(new HashMap<>());
//        query.get(indexOfQuery).put(key, value);
//        return this;
//    }
//
//    public FqlStringBuilder setOrderBy(Map<String, Boolean> orderBy) {
//        this.orderBy = orderBy;
//        return this;
//    }
//
//    public FqlStringBuilder setCount(boolean count) {
//        this.count = count;
//        return this;
//    }
//
//    public FqlStringBuilder setPropertiesSet(Map<String, Object> propertiesSet) {
//        this.propertiesSet = propertiesSet;
//        return this;
//    }
//
//    public String buildInsertString() {
//        return buildInsertString(this.items);
//    }
//
//    public String buildInsertString(T item) {
//        List<T> l = new ArrayList<>();
//        l.add(item);
//        return buildInsertString(l);
//    }
//
//    public String buildInsertString(List<T> items) {
//        if (items == null) throw new NullPointerException("Item can not be null when insert!");
//        StringBuilder sb = new StringBuilder();
//        StringBuilder vsb = new StringBuilder();
//        sb.append("INSERT INTO ")
//                .append(table)
//                .append("(");
//        vsb.append("(");
//        Field[] fs = items.get(0).getClass().getDeclaredFields();
//        for (int i=0; i<fs.length; i++) {
//            if (fs[i].isAnnotationPresent(model.mysql.annotation.Id.class) || fs[i].isAnnotationPresent(model.mysql.annotation.Ignore.class)) continue;
//            sb.append(fs[i].getName());
//            vsb.append("?");
//            if (i != fs.length - 1) {
//                sb.append(",");
//                vsb.append(",");
//            }
//        }
//
//        sb.append(") VALUES ");
//        vsb.append(")");
//        for (int i=0; i<items.size(); i++) {
//            sb.append(vsb);
//            if (i != items.size() - 1) {
//                sb.append(",\n");
//            }
//        }
//
//        return sb.toString();
//    }
//
//    public String buildUpdateString() {
//        StringBuilder sb = new StringBuilder();
//        if (table == null) throw new NullPointerException("Table can not be null when update a SQL record!");
//        if (query == null) throw new NullPointerException("Query can not be null when update a SQL record!");
//        sb.append("UPDATE ")
//                .append(table)
//                .append(" SET ");
//
//        Field[] fs = items.get(0).getClass().getDeclaredFields();
//
//        for (int i = 0; i < fs.length; i++) {
//            if (fs[i].isAnnotationPresent(model.mysql.annotation.Id.class) || fs[i].isAnnotationPresent(model.mysql.annotation.Ignore.class)) continue;
//            sb.append(fs[i].getName())
//                    .append("=?");
//            if (i != fs.length - 1) sb.append(",");
//        }
//        sb.append(" ")
//                .append(buildConditionString());
//
//        return sb.toString();
//    }
//
//    public String buildQueryString() {
//        StringBuilder sb = new StringBuilder();
//        if (table == null) throw new NullPointerException("Table can not be null when execute an SQL query!");
//
//        sb.append("SELECT * FROM ")
//                .append(table);
//        if (query != null) {
//            sb.append(" ")
//                .append(buildConditionString());
//        }
//        return sb.toString();
//    }
//
//    public StringBuilder buildConditionString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("WHERE ");
//
//        for (Map<String, String> m: query) {
//            sb.append("(");
//            for (Map.Entry<String, String> entry: m.entrySet()) {
//                sb.append(entry.getKey())
//                        .append(entry.getValue())
//                        .append("? AND ");
//            }
//            sb.replace(sb.lastIndexOf("AND"), sb.lastIndexOf("AND") + 1, "");
//            sb.append(") OR ");
//        }
//        sb.replace(sb.lastIndexOf("OR"), sb.lastIndexOf("OR") + 1, "");
//
//        return sb;
//    }
//
//    public String build() {
//        switch (type) {
//            case 1:
//                return buildInsertString();
//            case 2:
//                return buildQueryString();
//            case 3:
//                return buildUpdateString();
//            default:
//                return "";
//        }
//    }

    private StringBuilder sb;
    private boolean hasItem = false;

    /**
     * Constructor init the string builder
     */
    public FqlStringBuilder() {
        this.sb = new StringBuilder();
    }

    /**
     *
     * @param table name
     * @function set up insert into string
     * @return this FqlStringBuilder
     */
    public FqlStringBuilder insert(String table) {
        sb.append("INSERT INTO ")
                .append(table)
                .append(" ");
        return this;
    }

    /**
     *
     * @param item
     * @param <T>
     * @return this FqlStringBuilder
     * @function add the insert item to string builder. If has no item, the string will append fields in the begin, if has, the string will append ","
     * @throws IllegalAccessException
     */
    public <T> FqlStringBuilder addInsertItem(T item) throws IllegalAccessException {
        StringBuilder valSb = new StringBuilder();
        StringBuilder fieldSb = new StringBuilder();
        fieldSb.append("(");
        valSb.append("(");
        Field[] fs = item.getClass().getDeclaredFields();
        for (int i=0; i<fs.length; i++) {
            fs[i].setAccessible(true);

            Object temp;
            if ((temp = fs[i].get(item)) != null
                    && !fs[i].isAnnotationPresent(model.mysql.annotation.Id.class)
                    && !fs[i].isAnnotationPresent(model.mysql.annotation.Default.class)) {
                fieldSb.append(fs[i].getName());
                appendValue(valSb, temp);
                if (i != fs.length-1) {
                    valSb.append(",");
                    fieldSb.append(",");
                }
            }
        }

        fieldSb.append(") VALUES ");
        valSb.append(")");

        if (!hasItem) {
            sb.append(fieldSb);
        } else {
            sb.append(",\n");
            this.hasItem = true;
        }

        sb.append(valSb);

        return this;
    }

    /**
     *
     * @param table
     * @function set up the update String
     * @return this FqlStringBuilder
     */
    public FqlStringBuilder update(String table) {
        sb.append("UPDATE ")
                .append(table)
                .append(" SET ");
        return this;
    }

    /**
     *
     * @param item
     * @param <T>
     * @function set the item to update string
     * @return this FqlStringBuilder
     * @throws IllegalAccessException
     */
    public <T> FqlStringBuilder setItemUpdate(T item) throws IllegalAccessException {
        Field[] fs = item.getClass().getDeclaredFields();
        Field key = null;
        for (int i = 0; i < fs.length; i++) {
            fs[i].setAccessible(true);
            Object temp;
            if ((temp = fs[i].get(item)) != null) {
                if (!fs[i].isAnnotationPresent(model.mysql.annotation.Id.class)) {
                    sb.append(fs[i].getName())
                            .append("=");
                    appendValue(sb, temp);

                    if (i != fs.length-1) sb.append(",");
                }
                else key = fs[i];
            }
        }
        if (key == null) throw new NullPointerException("The entity must have an id field with annotation @Id");
        where(key.getName(), key.get(item));
        return this;
    }

    /**
     *
     * @param a
     * @param b
     * @function add where condition to string, with operation, if not, the operation will be "="
     * @return this FqlStringBuilder
     */
    public FqlStringBuilder where(String a, Object b) {
        if (a.contains(" ")) return where(a, null, b);
        return where(a, "=", b);
    }

    public FqlStringBuilder where(String a, String operation, Object b) {

        sb.append(" WHERE ");
        whereCondition(a, operation, b);

        return this;
    }

    public FqlStringBuilder where (Map<String, Object> query) {
        if (query != null) {
            int i = 0;
            for (Map.Entry<String, Object> en: query.entrySet()) {
                if (i == 0) where(en.getKey(), en.getValue());
                else whereAnd(en.getKey(), en.getValue());
                i++;
            }
        }

        return this;
    }

    public FqlStringBuilder select(String table) {
        sb.append("SELECT * FROM")
                .append(table);
        return this;
    }

    public FqlStringBuilder whereAnd(String a, Object b) {
        return whereAnd(a, "=", b);
    }

    public FqlStringBuilder whereAnd(String a, String operation, Object b) {
        sb.append(" AND ");
        whereCondition(a, operation, b);
        return this;
    }

    public FqlStringBuilder whereOr(String a, Object b) {
        return whereOr(a, "=", b);
    }

    public FqlStringBuilder whereOr(String a, String operation, Object b) {
        sb.append(" OR ");
        whereCondition(a, operation, b);
        return this;
    }

    private FqlStringBuilder whereCondition(String a, String operation, Object b) {
        sb.append(a);
        if (operation != null) sb.append(operation);
        appendValue(sb, b);
        return this;
    }

    public FqlStringBuilder limit(int limit) {
        sb.append(" LIMIT ")
                .append(limit);
        return this;
    }

    public FqlStringBuilder offset(int offset) {
        sb.append(" OFFSET ")
                .append(offset);
        return this;
    }

    /**
     *
     * @return sql string after build
     */
    public String build() {
        return sb.toString();
    }

    /**
     *
     * @param sb
     * @param val
     * @function append value with difference data type to a sql string builder
     */
    private void appendValue(StringBuilder sb, Object val) {
        switch (val.getClass().getSimpleName()) {
            case "Integer":
            case "Long":
                sb.append(val);
                break;
            case "Date":
                sb.append("'")
                        .append(new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format((Date)val))
                        .append("'");
                break;
            default:
                sb.append("'")
                        .append(val)
                        .append("'");
                break;
        }
    }
}
