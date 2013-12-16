package com.cloud.doc.model;

import com.cloud.security.model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_doc_mark")
public class DocMark {

    private String id;
    private User user;
    private DocFile file;

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = DocFile.class)
    @JoinColumn(name = "docId")
    public DocFile getFile() {
        return file;
    }

    public void setFile(DocFile file) {
        this.file = file;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
