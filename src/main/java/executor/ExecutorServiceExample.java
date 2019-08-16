package executor;

import consumer.ConsumerTask;
import provider.ProviderTask;

import java.util.concurrent.*;

public class ExecutorServiceExample {
    ExecutorService executor;

    ExecutorServiceExample() {
        try {
            executor = Executors.newFixedThreadPool(2);
            Future<?> future = executor.submit(new ProviderTask());
            Future<?> future2 = executor.submit(new ConsumerTask());
            future.get();
            future2.get();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Stop");
        } finally {
            executor.shutdown();
        }
    }
}
