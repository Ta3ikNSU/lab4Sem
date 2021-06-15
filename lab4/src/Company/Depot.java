package Company;

import Info.RailwayInfo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Depot {
    private final ConfigCompany configure; // конфигурация всего
    private final RailwayInfo railways;
    private Queue<Train> trains; // поезда
    private ScheduledExecutorService executor;

    public Depot(ConfigCompany configure, RailwayInfo railways) {
        this.railways = railways;
        this.trains = new ConcurrentLinkedQueue<>();
        this.executor = Executors.newScheduledThreadPool(configure.getTrainsNum());
        this.configure = configure;
        Logger.getLogger(getClass().getName()).info("New depot was created");
    }

    public void addNewTrain(String name) {
        Depot depot = this;
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                Train newTrain = new Train(name, configure, depot, railways);
                Logger.getLogger(getClass().getName()).info("New train was created");
                trains.add(newTrain);
                newTrain.start();
                Logger.getLogger(getClass().getName()).info("New train start work");
            }
        }, configure.getTrainInfo(name).getTimeToCreate(), TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executor.shutdownNow();
        trains.forEach(Thread::interrupt);
        Logger.getLogger(getClass().getName()).info("All train was stopped");
    }
}

