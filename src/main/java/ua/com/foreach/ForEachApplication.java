package ua.com.foreach;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForEachApplication implements CommandLineRunner {

    private final AppDemoData appDemoData;

    @Value("${runDemoData}")
    private boolean demoData;

    public ForEachApplication(AppDemoData appDemoData) {
        this.appDemoData = appDemoData;
    }

    public static void main(String[] args) {
        SpringApplication.run(ForEachApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (demoData) {
            appDemoData.createDemoData();
        }
    }
}
