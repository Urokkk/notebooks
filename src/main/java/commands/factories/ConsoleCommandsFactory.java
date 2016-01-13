package commands.factories;

import com.google.inject.Guice;
import com.google.inject.Injector;
import commands.*;
import commands.builders.*;
import services.DBModule;

/**
 * (c) Roman Gordeev
 * <p/>
 * 2014 июн 10
 *
 * Фабрика комманд консоли. Реализует паттерн Singleton.
 *
 */
public class ConsoleCommandsFactory extends CommandFacroryBase
{
    private static final ConsoleCommandsFactory instance = new ConsoleCommandsFactory();

    public static ConsoleCommandsFactory getInstance()
    {
        return instance;
    }

    private ConsoleCommandsFactory()
    {
        /*
          получаем объект Injector фреймворка Guice
          и создаем построители для наших комманд
         */
        Injector injector = Guice.createInjector(new DBModule());

        register(CommandAdd.NAME,  injector.getInstance(CommandAddBuilder.class));
        register(CommandList.NAME, injector.getInstance(CommandListBuilder.class));
        register(CommandExit.NAME, injector.getInstance(CommandExitBuilder.class));
        register(CommandDelete.NAME, injector.getInstance(CommandDeleteBuilder.class));
        register(CommandUpdate.NAME, injector.getInstance(CommandUpdateBuilder.class));
    }


}
