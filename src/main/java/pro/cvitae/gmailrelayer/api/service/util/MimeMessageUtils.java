package pro.cvitae.gmailrelayer.api.service.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import pro.cvitae.gmailrelayer.api.model.MessageHeaders;

public class MimeMessageUtils {

    private MimeMessageUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Tries to retrieve the given header from the message. If it is set more than
     * once and {@link IllegalArgumentException} is thrown. If not set or empty it
     * returns <code>null</code>. Else, it returns the header value.
     *
     * @param name of the header
     * @param msg
     * @return
     * @throws MessagingException
     */
    public static String getValidatedHeader(final String name, final MimeMessage msg) throws MessagingException {
        final String[] header = msg.getHeader(name);

        if (header == null || header.length == 0) {
            return null;
        }

        if (header.length > 1) {
            throw new IllegalArgumentException("Header " + name + " is set more than once");
        }

        final String aux = header[0];
        if ("".equals(aux) || aux == null) {
            return null;
        }

        return aux;
    }

    /**
     * Retrieves the 'X-Priority' header and tries to parse it.
     *
     * @param msg
     * @return If header exists and can be parsed, 1 to 5 value is returned. If
     *         header is not present or cannot be parsed a value of 3 (normal) is
     *         returned.
     * @throws MessagingException
     */
    public static int getPriority(final MimeMessage msg) throws MessagingException {
        final String header = getValidatedHeader(MessageHeaders.PRIORITY, msg);
        // If no header is present, normal (3) is returned.
        if (header == null) {
            return 3;
        }

        // Try to split, format of header is "3 (Normal)"
        final String[] parts = header.split(" ");
        if (parts.length == 0) {
            return 3;
        }

        // Try to parse the priority number
        try {
            return Integer.parseInt(parts[0]);
        } catch (final NumberFormatException nfe) {
            return 3;
        }
    }
}
