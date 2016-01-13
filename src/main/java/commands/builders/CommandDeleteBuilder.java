package commands.builders;

import com.google.inject.Inject;
import commands.Command;
import commands.CommandDelete;
import commands.factories.ConsoleCommandsFactory;
import model.Params;
import org.apache.commons.lang3.StringUtils;
import services.StorageService;

/**
 * Created by Юрий on 13.01.2016.
 */
public class CommandDeleteBuilder extends AbstractCommandBuilder {

    @Inject
    public CommandDeleteBuilder(StorageService storageService) {
        super(storageService);
    }

    @Override
    public Command createCommand(Params params) {
        String[] args = null;

        if (StringUtils.isNotEmpty(params.getCommandArgs()))
            args = StringUtils.split(params.getCommandArgs(), '|');

        if (args == null || args.length != 1)
            return ConsoleCommandsFactory.getInstance().createUnknownCommand(params);

        Command delete = new CommandDelete(args[0], getStorage());

        return delete;
    }
}
