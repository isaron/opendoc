package com.cloud.doc.service;

import com.cloud.attach.Attach;
import com.cloud.doc.model.DocFile;
import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkService {

    @Autowired
    private IDao dao;

    /**
     * search my mark files
     *
     * @return
     */
    public List<DocFile> searchStarFiles() {

        String hql = "select distinct f,a from DocFile f,Attach a,DocMark m where f.id = a.entityId"
                + " and f.uniqueId = m.file.uniqueId and m.user.id = ? and f.isLatest = ? order by f.createTime desc";

        List<Object[]> list = dao.getAllByHql(hql, new Object[] {Constants.getLoginUserId(), Constants.VALID_YES});

        return combineAttach(list);
    }

    /**
     * search my operate files
     *
     * @return
     */
    public List<DocFile> searchOperateFiles() {

        String hql = "select distinct f,a from DocFile f,Attach a,DocRecord r where f.id = a.entityId"
                + " and f.uniqueId = (select f2.uniqueId from DocFile f2 where f2.id = r.docId)"
                + " and r.creator = ? and f.isLatest = ? order by r.createTime desc";

        List<Object[]> list = dao.getAllByHql(hql, new Object[] {Constants.getLoginUserId(), Constants.VALID_YES});

        return combineAttach(list);
    }

    /**
     * search my upload files
     *
     * @return
     */
    public List<DocFile> searchUploadFiles() {

        String hql = "select f,a from DocFile f,Attach a where f.id = a.entityId and f.creator = ?"
                + " and f.isLatest = ? order by f.createTime desc";

        List<Object[]> list = dao.getAllByHql(hql, new Object[] {Constants.getLoginUserId(), Constants.VALID_YES});

        return combineAttach(list);
    }

    /**
     * combine attach info to docfile
     *
     * @param list
     * @return
     */
    private List<DocFile> combineAttach(List<Object[]> list) {

        List<DocFile> uploadFiles = new ArrayList();

        for(Object[] obj : list) {
            DocFile file = (DocFile) (obj[0]);
            Attach attach = (Attach) (obj[1]);

            file.setAttach(attach);
            uploadFiles.add(file);
        }

        return uploadFiles;
    }
}
