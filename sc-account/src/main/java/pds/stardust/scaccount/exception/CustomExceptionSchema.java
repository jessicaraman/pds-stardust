package pds.stardust.scaccount.exception;

/**
 * CustomerExceptionSchema
 */
public class CustomExceptionSchema {
    private int id;
    private String message;
    private String details;

    public CustomExceptionSchema(int id, String message, String details) {
        this.id = id;
        this.message = message;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
