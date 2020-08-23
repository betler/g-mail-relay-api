package pro.cvitae.gmailrelayer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.cvitae.gmailrelayer.persistence.entity.Message;

public interface MessageDao extends JpaRepository<Message, Long> {

}
