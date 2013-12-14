package com.cloud.doc.service;

import com.cloud.doc.model.DocFile;
import com.cloud.platform.Constants;
import com.cloud.platform.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {

    @Autowired
    private IDao dao;

    /**
     * search upload files
     *
     * @return
     */
    public List<DocFile> searchUploadFiles() {

        return dao.getAllByHql("from DocFile where creator = ?", Constants.getLoginUserId());
    }
}
