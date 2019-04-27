package com.jenkins.own.moudle.app.entity;

import lombok.Data;
import org.springframework.core.io.FileSystemResource;

/**
 * @Author: feige
 * @Date: Created in 12:00 2019/2/13
 * @Description:
 */
@Data
public class Mail{
    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 附件
     */
    private FileSystemResource file;

    /**
     * 附件名称
     */
    private String attachmentFilename;

    private String sender;

    private String toWho;

    /**
     * 内容ID，用于发送静态资源邮件时使用
     */
    private String contentId;

    public static Mail getMailBean() {
        return new Mail();
    }

}

