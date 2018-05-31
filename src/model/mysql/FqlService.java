package model.mysql;

public class FqlService {
    private static String url;

    public static void connect(String url) {
        FqlService.url = url;
    }

    public static <T> FqlModel getModel(String table) {
        FqlModel<T> m = new FqlModel<>(table, FqlService.url);
        return m;
    }

    public static <T> FqlModel getModel(String table, String url) {
        FqlModel<T> m = new FqlModel<>(table, url);
        return m;
    }
}
