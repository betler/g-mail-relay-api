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
@Table(name = "header")
public class Header {

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
    @Column(name = "name", nullable = true, length = 200)
    private String name;

    @Getter
    @Setter
    @Column(name = "value", nullable = true, length = 2000)
    private String value;

}
