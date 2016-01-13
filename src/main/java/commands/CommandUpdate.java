package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
 * Created by Юрий on 13.01.2016.
 */
public class CommandUpdate extends AbstractCommand {

    public static final String NAME = "update";

    public CommandUpdate(String id, String person, String phone, String address, StorageService storage)
    {
        super(storage);
        this.id = id;
        this.person = person;
        this.phone = phone;
        this.address = address;
    }


    @Override
    public void execute(ApplicationContext ap) {
        getStorage().update(id, person, phone, address);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private  String id;
    private  String person;
    private  String phone;
    private  String address;
}
