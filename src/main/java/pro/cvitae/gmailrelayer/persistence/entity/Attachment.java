package pro.cvitae.gmailrelayer.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Getter
    @Setter
    @Column(name = "message_id", nullable = false)
    private Long messageId;

    @Getter
    @Setter
    @Column(name = "cid", nullable = true, length = 200)
    private String cid;

    @Getter
    @Setter
    @Column(name = "content_type", nullable = true, length = 200)
    private String contentType;

    @Getter
    @Setter
    @Column(name = "filename", nullable = true, length = 50)
    private String filename;

    @Getter
    @Setter
    @Column(name = "value", nullable = false)
    private String value;
}
