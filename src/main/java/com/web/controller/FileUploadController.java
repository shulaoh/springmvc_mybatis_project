package com.web.controller;

import com.web.data.pojo.FileContent;
import com.web.form.UserForm;
import com.web.service.IFileContentService;
import com.web.utils.Tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    private static Logger logger = Logger.getLogger(FileUploadController.class);

    @Resource
    private IFileContentService fileContentService;

    @RequestMapping(value = {"/upload"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public String upload(HttpServletRequest request, String fileDesc, @RequestParam("image") MultipartFile file)
            throws Exception {
        logger.info("upload beginning [" + file.getName() + "]");

        if (!(file.isEmpty())) {
            String path = request.getServletContext().getRealPath("/image/");

            String filename = file.getOriginalFilename();
            Calendar date = Calendar.getInstance();
            SimpleDateFormat datef = new SimpleDateFormat("yyMMddHHmmss");
            String extension = filename.substring(filename.lastIndexOf("."));

            String filefullName = filename.substring(0, filename.length() - extension.length());//得到文件名。去掉了后缀
            String uploadFileName = filefullName + "_" + datef.format(date.getTime()) + extension;
            File filepath = new File(path, uploadFileName);

            if (!(filepath.getParentFile().exists())) {
                filepath.getParentFile().mkdirs();
            }

            file.transferTo(new File(path + File.separator + uploadFileName));

            return uploadFileName;
        }
        return "{\"result\":{\"retcode\":-1,\"retmsg\":\"creat file error.\"}}";
    }

    @RequestMapping(value = {"/upload2"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public String upload2(HttpServletRequest request, @ModelAttribute UserForm userForm, Model model)
            throws Exception {
        if (!(userForm.getImage().isEmpty())) {
            String path = request.getServletContext().getRealPath("/image/");

            String filename = userForm.getImage().getOriginalFilename();
            File filepath = new File(path, filename);

            if (!(filepath.getParentFile().exists())) {
                filepath.getParentFile().mkdirs();
            }

            userForm.getImage().transferTo(new File(path + File.separator + filename));

            model.addAttribute("user", userForm);
            return "fileDownload";
        }
        return "error";
    }

    @RequestMapping({"/download"})
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename, Model model)
            throws Exception {
        String path = request.getServletContext().getRealPath("/images/");
        File file = new File(path + File.separator + filename);
        HttpHeaders headers = new HttpHeaders();

        String downloadFielName = new String(filename.getBytes("UTF-8"), "iso-8859-1");

        headers.setContentDispositionFormData("attachment", downloadFielName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @RequestMapping({"/download2"})
    public ResponseEntity<byte[]> download2(HttpServletRequest request, @RequestParam("fileId") String fileId, Model model)
            throws Exception {
        String path = request.getServletContext().getRealPath("/images/");
        FileContent fc = fileContentService.getFileContentById(fileId);
        File file = new File(path + File.separator + fc.getFileURL());
        HttpHeaders headers = new HttpHeaders();

        String downloadFielName = new String(fc.getFileName().getBytes("UTF-8"), "iso-8859-1");

        headers.setContentDispositionFormData("attachment", downloadFielName);

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}