package commands.builders;

import com.google.inject.Inject;
import commands.Command;
import commands.CommandDelete;
import commands.CommandUpdate;
import commands.factories.ConsoleCommandsFactory;
import model.Params;
import org.apache.commons.lang3.StringUtils;
import services.StorageService;

/**
 * Created by Юрий on 13.01.2016.
 */
public class CommandUpdateBuilder extends AbstractCommandBuilder {
    @Inject
    public CommandUpdateBuilder(StorageService storageService) {
        super(storageService);
    }

    @Override
    public Command createCommand(Params params)
    {
        String[] args = null;

        if (StringUtils.isNotEmpty(params.getCommandArgs()))
            args = StringUtils.split(params.getCommandArgs(), '|');

        if (args == null || args.length != 4)
            return ConsoleCommandsFactory.getInstance().createUnknownCommand(params);

        Command update = new CommandUpdate(args[0], args[1], args[2], args[3], getStorage());

        return update;
    }
}
