package pro.cvitae.gmailrelayer.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.EmailAttachment;
import pro.cvitae.gmailrelayer.api.model.EmailHeader;
import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.EmailStatus;
import pro.cvitae.gmailrelayer.api.service.IMailPersistenceService;
import pro.cvitae.gmailrelayer.persistence.entity.Address;
import pro.cvitae.gmailrelayer.persistence.entity.Attachment;
import pro.cvitae.gmailrelayer.persistence.entity.Body;
import pro.cvitae.gmailrelayer.persistence.entity.Header;
import pro.cvitae.gmailrelayer.persistence.entity.Message;
import pro.cvitae.gmailrelayer.persistence.repository.AddressDao;
import pro.cvitae.gmailrelayer.persistence.repository.AttachmentDao;
import pro.cvitae.gmailrelayer.persistence.repository.HeaderDao;
import pro.cvitae.gmailrelayer.persistence.repository.MessageDao;
import pro.cvitae.gmailrelayer.persistence.repository.helper.MessageHelper;
import pro.cvitae.gmailrelayer.persistence.tx.Tx;

@Service
public class MailPersistenceService implements IMailPersistenceService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    AttachmentDao attachmentDao;

    @Autowired
    HeaderDao headerDao;

    @Tx
    @Override
    public Long saveMessage(final EmailMessage message, final EmailStatus status) {
        final Message entity = MessageHelper.from(message);

        entity.setFrom(this.checkCreateAddress(message.getFrom()));
        entity.setReplyTo(this.checkCreateAddress(message.getReplyTo()));
        entity.setStatus(status.value());
        entity.setNotBefore(message.getNotBefore());

        this.setToList(message, entity);
        this.setCcList(message, entity);
        this.setBccList(message, entity);

        // Save message
        final Long messageId = this.messageDao.save(entity).getId();

        // Set body, must be after saving
        final Body body = new Body(message.getBody());
        body.setMessage(entity);
        entity.setBody(body);

        // Same for attachments
        if (message.getAttachments() != null) {
            for (final EmailAttachment emailAttachment : message.getAttachments()) {
                final Attachment attachment = new Attachment();
                attachment.setCid(emailAttachment.getCid());
                attachment.setFilename(emailAttachment.getFilename());
                attachment.setValue(emailAttachment.getContent());
                attachment.setMessageId(messageId);
                attachment.setContentType(emailAttachment.getContentType());
                this.attachmentDao.save(attachment);
            }
        }

        // Same for headers
        if (message.getHeaders() != null) {
            for (final EmailHeader emailHeader : message.getHeaders()) {
                final Header header = new Header();
                header.setMessageId(messageId);
                header.setName(emailHeader.getName());
                header.setValue(emailHeader.getValue());
                this.headerDao.save(header);
            }
        }

        return messageId;
    }

    @Override
    public Long saveMessage(final MimeMessage message, final EmailStatus sent) {
        try {
            final Message entity = MessageHelper.from(message);
        } catch (final MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Tx
    @Override
    public void updateStatus(final Long messageId, final EmailStatus status) {
        final Message message = this.messageDao.getOne(messageId);
        message.setStatus(status.value());
        this.messageDao.save(message);
    }

    private void setToList(final EmailMessage message, final Message entity) {
        final List<Address> toList = new ArrayList<>();
        for (final String addr : message.getTo()) {
            toList.add(this.checkCreateAddress(addr));
        }
        entity.setTo(toList);

    }

    private void setCcList(final EmailMessage message, final Message entity) {
        final List<Address> ccList = new ArrayList<>();
        for (final String addr : message.getTo()) {
            ccList.add(this.checkCreateAddress(addr));
        }
        entity.setCc(ccList);

    }

    private void setBccList(final EmailMessage message, final Message entity) {
        final List<Address> bccList = new ArrayList<>();
        for (final String addr : message.getTo()) {
            bccList.add(this.checkCreateAddress(addr));
        }
        entity.setBcc(bccList);

    }

    private Address checkCreateAddress(final String value) {
        if (value == null) {
            return null;
        }

        Address address = this.addressDao.findByValue(value);
        if (address == null) {
            address = this.addressDao.save(new Address(value));
        }

        return address;
    }

}