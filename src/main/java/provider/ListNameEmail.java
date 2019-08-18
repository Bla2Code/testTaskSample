package provider;

public interface ListNameEmail {

    /**
     * Adds a name for sending email messages
     * @param name
     */
    void setName(String name);

    /**
     * Add email address for mailing
     * @param email
     */
    void setEmail(String email);

    /**
     * Get Name
     * @return String Name
     */
    String getName();

    /**
     * Get Email
     * @return String Email
     */
    String getEmail();

    /**
     * A function for receiving email distribution addresses
     * @return void
     */
     void getTaskList();
}
