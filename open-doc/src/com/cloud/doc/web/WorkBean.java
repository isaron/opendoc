package com.cloud.doc.web;

import com.cloud.doc.model.Directory;
import com.cloud.doc.model.DocFile;
import com.cloud.doc.service.WorkService;
import com.cloud.doc.util.DocUtil;
import com.cloud.platform.Constants;
import com.cloud.platform.SearchVo;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("work")
public class WorkBean {

    private static Logger logger = Logger.getLogger(WorkBean.class);

    @Autowired
    private WorkService workService;

    /**
     * show more files
     *
     * @param type
     * @param page
     * @return
     */
    @ResponseBody
    @RequestMapping("/showMore.do")
    public String showMore(@RequestParam("type") int type, @RequestParam("page") int page) {

        SearchVo searchVo = new SearchVo();
        searchVo.setPage(page);
        searchVo.setPageSize(DocUtil.WORK_FILE_SIZE);

        List<DocFile> files = new ArrayList();

        try {
            switch (type) {
                case 0:
                    files = workService.searchStarFiles(searchVo);
                    break;
                case 1:
                    files = workService.searchUploadFiles(searchVo);
                    break;
                case 2:
                    files = workService.searchOperateFiles(searchVo);
                    break;
            }

        } catch (Exception e) {
            logger.error("***** 异常信息 ***** 方法：showMore", e);
        }

        JSONObject result = new JSONObject();
        result.put("files", files);
        result.put("hasMore", searchVo.getPageNum() > page ? Constants.VALID_YES : Constants.VALID_NO);

        return result.toString();
    }

    /**
     * init my workspace data
     *
     * @return
     */
    @RequestMapping("/openWork.do")
    public ModelAndView openWork() {

        ModelAndView mv = new ModelAndView("work/work");

        try {
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

        } catch (Exception e) {
            logger.error("***** 异常信息 ***** 方法：openWork", e);
        }

        return mv;
    }
}
