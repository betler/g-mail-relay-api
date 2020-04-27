package pro.cvitae.gmailrelayer.api.model;

public abstract class MessageHeaders {

    public static final String APPLICATION_ID = "X-GMR-APPLICATION-ID";
    public static final String MESSAGE_TYPE = "X-GMR-MESSAGE-TYPE";
    public static final String ASYNC = "X-GMR-ASYNC";

    private MessageHeaders() {
        throw new UnsupportedOperationException();
    }

}
