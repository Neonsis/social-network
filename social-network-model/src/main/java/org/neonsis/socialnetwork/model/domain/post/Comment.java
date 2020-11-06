package org.neonsis.socialnetwork.model.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neonsis.socialnetwork.model.domain.base.BaseEntityAudit;
import org.neonsis.socialnetwork.model.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntityAudit implements Serializable {

    private static final long serialVersionUID = 5737119101681051526L;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reply_comment_id")
    private Comment replyTo;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
