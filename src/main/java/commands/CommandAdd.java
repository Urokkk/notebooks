package commands;

import controllers.ApplicationContext;
import services.StorageService;

/**
* User: rgordeev
* Date: 25.06.14
* Time: 17:20
*/
public class CommandAdd extends AbstractCommand
{
    public static final String NAME = "add";


    public CommandAdd(String person, String phone, String address, StorageService storage)
    {
        super(storage);
        this.person = person;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public void execute(ApplicationContext ap)
    {

        getStorage().add(this.person, this.phone, this.address);

        System.out.println(getName() + ": person " + this.person + " was added to the book, phone is: " + this.phone + " address is: " + this.address);
    }

    @Override
    public String getName() {
        return NAME;
    }

    private String person;
    private String phone;
    private String address;

}
