package executor;

import consumer.ConsumerTask;
import org.apache.log4j.Logger;
import provider.ProviderTask;
import java.util.concurrent.*;

class ExecutorServiceExample {
    private ExecutorService executor;
    private static final Logger log = Logger.getLogger("EmailEmulator");
    ExecutorServiceExample() {
        try {
            executor = Executors.newFixedThreadPool(2);
            Future<?> future = executor.submit(new ProviderTask());
            Future<?> future2 = executor.submit(new ConsumerTask());
            future.get();
            future2.get();
        } catch (ExecutionException | InterruptedException e) {
            log.info("... Stop!");
        } finally {
            assert executor != null;
            executor.shutdown();
        }
    }
}
