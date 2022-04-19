package banks.client.config;

public final class Config {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static final String API_ADDRESS = String.format("http://%s:%d", HOST, PORT);

    private Config(){}
}
