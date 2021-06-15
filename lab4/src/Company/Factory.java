package Company;

import java.util.logging.Logger;

public class Factory extends Thread {
    public final Warehouse warehouse;
    private final String itemName;
    private final int timeForCreate;

    public Factory(String itemName, Warehouse warehouse, int timeForCreate) {
        this.warehouse = warehouse;
        this.itemName = itemName;
        this.timeForCreate = timeForCreate;

        Logger.getLogger(getClass().getName()).info("New Factory was created");
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Thread.sleep(timeForCreate);
                Item newItem = new Item(itemName);
                warehouse.addItem(newItem);

                Logger.getLogger(getClass().getName()).info("Factory creat goods: " + itemName);
            } catch (InterruptedException e) {
                e.printStackTrace();

                return;
            }
        }
        Logger.getLogger(getClass().getName()).info("Factory was stopped");

    }
}
