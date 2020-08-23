package pro.cvitae.gmailrelayer.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pro.cvitae.gmailrelayer.api.model.EmailMessage;
import pro.cvitae.gmailrelayer.api.model.EmailStatus;
import pro.cvitae.gmailrelayer.api.service.IMailPersistenceService;
import pro.cvitae.gmailrelayer.persistence.entity.Address;
import pro.cvitae.gmailrelayer.persistence.entity.Message;
import pro.cvitae.gmailrelayer.persistence.repository.AddressDao;
import pro.cvitae.gmailrelayer.persistence.repository.MessageDao;
import pro.cvitae.gmailrelayer.persistence.repository.helper.MessageHelper;
import pro.cvitae.gmailrelayer.persistence.tx.Tx;

@Service
public class MailPersistenceService implements IMailPersistenceService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    AddressDao addressDao;

    @Tx
    @Override
    public Long saveMessage(final EmailMessage message, final EmailStatus status) {
        final Message entity = MessageHelper.from(message);

        entity.setFrom(this.checkCreateAddress(message.getFrom()));
        // entity.setReplyTo(this.checkCreateAddress(message.getReplyTo()));

        final List<Address> toList = new ArrayList<>();
        for (final String addr : message.getTo()) {
            toList.add(this.checkCreateAddress(addr));
        }
        entity.setTo(toList);

        entity.setStatus(status.value());

        return this.messageDao.save(entity).getId();
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
