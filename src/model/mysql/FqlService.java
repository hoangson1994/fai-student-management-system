package model.mysql;

public class FqlService {
    private static String url;

    public static void connect(String url) {
        FqlService.url = url;
    }

    public static FqlModel getModel(String table) {
        return new FqlModel(table, FqlService.url);
    }

    public static FqlModel getModel(String table, String url) {
        return new FqlModel(table, url);
    }
}
