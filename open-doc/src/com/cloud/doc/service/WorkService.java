package com.cloud.doc.service;

import com.cloud.attach.Attach;
import com.cloud.doc.model.DocFile;
import com.cloud.platform.Constants;
import com.cloud.platform.DocConstants;
import com.cloud.platform.IDao;
import com.cloud.platform.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public List<DocFile> searchStarFiles(SearchVo searchVo) throws Exception {

        String hql = "select distinct f,a from DocFile f,Attach a,DocMark m where f.id = a.entityId"
                + " and f.uniqueId = m.file.uniqueId and m.user.id = ? and f.isLatest = ? order by f.createTime desc";

        List<Object[]> list = dao.getPageByHql(hql, new Object[] {Constants.getLoginUserId(), Constants.VALID_YES}, searchVo);

        return combineAttach(list);
    }

    /**
     * search my operate files
     *
     * @return
     */
    public List<DocFile> searchOperateFiles(SearchVo searchVo) throws Exception {

        String hql = "select distinct f,a from DocFile f,Attach a,DocRecord r where f.id = a.entityId"
                + " and f.uniqueId = (select f2.uniqueId from DocFile f2 where f2.id = r.docId)"
                + " and r.creator = ? and f.isLatest = ? order by r.createTime desc";

        List<Object[]> list = dao.getPageByHql(hql, new Object[] {Constants.getLoginUserId(), Constants.VALID_YES}, searchVo);

        return combineAttach(list);
    }

    /**
     * search my upload files
     *
     * @return
     */
    public List<DocFile> searchUploadFiles(SearchVo searchVo) throws Exception {

        String hql = "select f,a from DocFile f,Attach a where f.id = a.entityId and f.creator = ?"
                + " and f.isLatest = ? order by f.createTime desc";

        List<Object[]> list = dao.getPageByHql(hql, new Object[] {Constants.getLoginUserId(), Constants.VALID_YES}, searchVo);

        return combineAttach(list);
    }

    /**
     * combine attach info to docfile
     *
     * @param list
     * @return
     */
    private List<DocFile> combineAttach(List<Object[]> list) throws IOException {

        List<DocFile> uploadFiles = new ArrayList();

        for(Object[] obj : list) {
            DocFile file = (DocFile) (obj[0]);
            Attach attach = (Attach) (obj[1]);

            file.setAttach(attach);
            file.setFileBgStyle(DocConstants.getFileBgStyle(attach.getExtendType()));

            uploadFiles.add(file);
        }

        return uploadFiles;
    }
}
