package pro.cvitae.gmailrelayer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.cvitae.gmailrelayer.persistence.entity.Attachment;

public interface AttachmentDao extends JpaRepository<Attachment, Long> {

}
