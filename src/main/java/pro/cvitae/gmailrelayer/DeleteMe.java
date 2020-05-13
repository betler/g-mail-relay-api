/**
 * g-mail-relayer smtp mail relayer and API for sending emails
 * Copyright (C) 2020  https://github.com/betler
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package pro.cvitae.gmailrelayer;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DeleteMe {

    public static void main(final String[] args) {
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.auth", "false"); // If you need to authenticate
            props.put("mail.smtp.socketFactory.port", 25);

            props.put("mail.smtp.host", "localhost");
            props.put("mail.smtp.port", "25");

            // Get the default Session object.
            final Session session = Session.getDefaultInstance(props);

            final MimeMessage msg = new MimeMessage(session);
            // set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("X-GMR-APPLICATION-ID", "APP1");
            msg.addHeader("X-GMR-MESSAGE-TYPE", "Password Recovery");
            msg.addHeader("X-GMR-ASYNC", "truess");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("mgutierrez@c-vitae.pro", "NoReply-JD"));

            // msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject("SUBJECT", "UTF-8");

            msg.setText("<h1>It works!</h1>", "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mikel.gutierrez@gmail.com", false));
            System.out.println("Message is ready");
            Transport.send(msg);

            System.out.println("EMail Sent Successfully!!");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
