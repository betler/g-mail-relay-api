package pro.cvitae.gmailrelayer.api;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-04-04T22:29:14.146Z[GMT]")
public class ApiException extends Exception {

    private static final long serialVersionUID = -2448856336044008380L;

    private final int code;

    public ApiException(final int code, final String msg) {
        super(msg);
        this.code = code;
    }
}
