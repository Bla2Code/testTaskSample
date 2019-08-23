package provider;

import java.util.Random;

public class TaskList implements ListNameEmail {

    private String name;
    private String email;
    private final Random random = new Random();

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public TaskList() {
        this.getTaskList();
    }

    public synchronized void getTaskList() {
        int rand = random.nextInt();
        this.setName("Name" + rand);
        this.setEmail("Name" + rand + "@ci.ru");
    }

}
