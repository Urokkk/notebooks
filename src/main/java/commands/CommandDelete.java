package commands;

import services.StorageService;
import controllers.ApplicationContext;
import java.sql.PreparedStatement;

/**
 * Created by Юрий on 10.01.2016.
 */
public class CommandDelete extends AbstractCommand
{
    public static final String NAME = "delete";

    public  CommandDelete(String id, StorageService storage)
    {
        super(storage);
        this.id = id;
    }

    @Override
    public void execute(ApplicationContext ap)
    {
        getStorage().delete(id);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private String id;
}
