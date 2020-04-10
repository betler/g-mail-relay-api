package pro.cvitae.gmailrelayer.api.exception;

import java.util.List;

import pro.cvitae.gmailrelayer.api.model.ErrorDetail;

public class ErrorDetailException extends Exception {

    private static final long serialVersionUID = -46898138093062942L;

    private final String code;

    private final String description;

    private final List<String> params;

    public ErrorDetailException(final Throwable cause, final String code, final String description,
            final List<String> params) {
        super(cause);
        this.code = code;
        this.description = description;
        this.params = params;
    }

    public ErrorDetail getDetail() {
        final ErrorDetail detail = new ErrorDetail();
        detail.setCode(this.code);
        detail.setDescription(this.description);
        detail.setParams(this.params);

        return detail;
    }

}
