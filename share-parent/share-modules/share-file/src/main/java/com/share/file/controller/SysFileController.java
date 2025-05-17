package com.share.file.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.share.common.core.domain.R;
import com.share.common.core.utils.file.FileUtils;
import com.share.file.service.ISysFileService;
import com.share.system.api.domain.SysFile;

/**
 * 文件请求处理
 *
 * @author share
 */
@RestController
public class SysFileController
{
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    @Qualifier("minioSysFileService")
    private ISysFileService sysFileService;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file)
    {
        try
        {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        }
        catch (Exception e)
        {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }
}
