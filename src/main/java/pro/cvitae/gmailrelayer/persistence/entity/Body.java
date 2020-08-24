package pro.cvitae.gmailrelayer.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "body")
public class Body {

    public Body() {
    }

    public Body(final String body) {
        this.body = body;
    }

    @Id
    @Getter
    @Setter
    @Column(name = "message_id", nullable = false, updatable = false, unique = true)
    private Long messageId;

    @Getter
    @Setter
    @Column(name = "body", nullable = false)
    private String body;

    @Getter
    @Setter
    @MapsId
    @OneToOne
    @JoinColumn(name = "messageId")
    private Message message;
}
