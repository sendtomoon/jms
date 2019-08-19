package com.jy.controller.scm.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.jy.service.scm.attachment.AttachmentServiceImpl;

public class AttachmentUtil {
	 public static String fileServerAddr;    
	 public final static String protocol = "http://";
	 public final static String separator = "/";
	 
    static  {    
       Properties prop =  new  Properties();    
       InputStream in = AttachmentServiceImpl.class.getResourceAsStream( "/upload.properties" );    
        try  {    
           prop.load(in);    
           fileServerAddr = prop.getProperty("fileServerAddr").trim();    
       }  catch  (IOException e) {    
           e.printStackTrace();    
       }    
   }
}