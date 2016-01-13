package configs;

/**
 * User: rgordeev
 * Date: 25.06.14
 * Time: 17:56
 *
 * Класс конфигурации подключения к БД,
 * используется в {@link services.JDBCStorageService.TransactionScript}
 * и {@link services.DBModule#getFactory()}
 *
 */
public enum DBConnection
{
    HIBERNATE   ("jdbc:postgresql://localhost/1234",  "postgres", "12345678"),
    JDBC        ("jdbc:postgresql://localhost/1234",   "postgres", "12345678");


    private DBConnection(String url, String username, String password)
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String url()
    {
        return url;
    }

    public String username()
    {
        return username;
    }

    public String password()
    {
        return password;
    }

    private String url;
    private String username;
    private String password;
}
