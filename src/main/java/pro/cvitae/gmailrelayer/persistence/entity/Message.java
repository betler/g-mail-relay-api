package pro.cvitae.gmailrelayer.persistence.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Getter
    @Setter
    @Column(name = "application_id", nullable = true, length = 30)
    private String applicationId;

    @Getter
    @Setter
    @Column(name = "message_type", nullable = true, length = 30)
    private String messageType;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_addr", referencedColumnName = "id")
    private Address from;

//    @Getter
//    @Setter
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "reply_to", referencedColumnName = "id")
//    private Address replyTo;
//
//    @Getter
//    @Setter
//    @Column(name = "subject", nullable = true, length = 255)
//    private String subject;
//
    @Getter
    @Setter
    @Column(name = "text_format", nullable = false)
    private Short textFormat;

    @Getter
    @Setter
    @Column(name = "text_encoding", nullable = false, length = 30)
    private String textEncoding;

    @Getter
    @Setter
    @Column(name = "delivery_type", nullable = false)
    private Short deliveryType;

    @Getter
    @Setter
    @Column(name = "priority", nullable = false)
    private Short priority;

//    @Getter
//    @Setter
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "not_before", nullable = true)
//    private Date notBefore;
//
    @Getter
    @Setter
    @Column(name = "status", nullable = false)
    private Short status;

//    @Getter
//    @Setter
//    @Column(name = "retries", nullable = false)
//    private Short retries;
//
    @Getter
    @Setter
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", nullable = false)
    private Date creationTime;

//    @Getter
//    @Setter
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "last_retry_date", nullable = true)
//    private Date lastRetryDate;
}
