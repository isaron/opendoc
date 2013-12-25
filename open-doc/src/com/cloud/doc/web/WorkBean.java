package com.cloud.doc.web;

import com.cloud.doc.model.Directory;
import com.cloud.doc.model.DocFile;
import com.cloud.doc.service.WorkService;
import com.cloud.doc.util.DocUtil;
import com.cloud.platform.Constants;
import com.cloud.platform.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("work")
public class WorkBean {

    @Autowired
    private WorkService workService;

    /**
     * init my workspace data
     *
     * @return
     */
    @RequestMapping("/openWork.do")
    public ModelAndView openWork() {

        ModelAndView mv = new ModelAndView("work/work");

        // search my mark files
        SearchVo starVo = new SearchVo();
        starVo.setPage(1);
        starVo.setPageSize(DocUtil.WORK_FILE_SIZE);

        List<DocFile> starFiles = workService.searchStarFiles(starVo);
        mv.addObject("starFiles", starFiles);
        mv.addObject("starMore", starVo.getPageNum() > 1 ? Constants.VALID_YES : Constants.VALID_NO);

        // search my upload files
        SearchVo uploadVo = new SearchVo();
        uploadVo.setPage(1);
        uploadVo.setPageSize(DocUtil.WORK_FILE_SIZE);

        List<DocFile> uploadFiles = workService.searchUploadFiles(uploadVo);
        mv.addObject("uploadFiles", uploadFiles);
        mv.addObject("uploadMore", uploadVo.getPageNum() > 1 ? Constants.VALID_YES : Constants.VALID_NO);

        // search my operate files
        SearchVo operateVo = new SearchVo();
        operateVo.setPage(1);
        operateVo.setPageSize(DocUtil.WORK_FILE_SIZE);

        List<DocFile> operateFiles = workService.searchOperateFiles(operateVo);
        mv.addObject("operateFiles", operateFiles);
        mv.addObject("operateMore", operateVo.getPageNum() > 1 ? Constants.VALID_YES : Constants.VALID_NO);

        return mv;
    }
}
