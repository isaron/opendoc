package com.cloud.doc.web;

import com.cloud.doc.model.Directory;
import com.cloud.doc.model.DocFile;
import com.cloud.doc.service.WorkService;
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

        // search my upload files
        List<DocFile> uploadFiles = workService.searchUploadFiles();
        mv.addObject("uploadFiles", uploadFiles);

        // search my operate files
        List<DocFile> operateFiles = workService.searchOperateFiles();
        mv.addObject("operateFiles", operateFiles);

        return mv;
    }
}
