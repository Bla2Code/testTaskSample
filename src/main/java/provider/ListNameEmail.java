package provider;

public interface ListNameEmail {

    /**
     * Add a name for sending email messages
     * @param name name
     */
    void setName(String name);

    /**
     * Add email address for mailing
     * @param email email
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
     */
     void getTaskList();
}
