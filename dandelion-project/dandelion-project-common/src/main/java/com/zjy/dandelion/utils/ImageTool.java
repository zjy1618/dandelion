package com.zjy.dandelion.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 图片工具
 * @author Po
 *
 */
public class ImageTool {
	
    public static String dirSplit = "\\";//linux windows

	/**
	 * 是否为允许图片类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isImageAllowType(String fileName) {
		String typeName = "";
		// 扩展名格式：
		if (fileName.lastIndexOf(".") >= 0) {
			typeName = fileName.substring(fileName.lastIndexOf("."));
		}
		// 定义允许上传的文件类型
		List<String> fileTypes = new ArrayList<String>();
		fileTypes.add(".jpg");
		fileTypes.add(".jpeg");
		fileTypes.add(".png");
		fileTypes.add(".bmp");
		return fileTypes.contains(typeName.toLowerCase());
	}

    /**
     * 
     * @param image
     * @param realPath 绝对路径（不包含文件名）
     * @return 文件名+格式
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String upload(MultipartFile image, String realPath, String fileName)
    {
        if (image != null && StringUtils.isNotEmpty(realPath))
        {
            String imageFileName = image.getOriginalFilename();
            String typeName = "";
            // 扩展名格式：
            if (imageFileName.lastIndexOf(".") >= 0)
            {
                typeName = imageFileName.substring(imageFileName.lastIndexOf("."));
            }
            
            String newFileName = fileName + typeName;
            File pathFile = new File(realPath);
            if (!pathFile.exists())
            {
                pathFile.mkdirs();
            }
            String toPath = realPath + newFileName;
            try
            {
                image.transferTo(new File(toPath));
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
                return null;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
            return newFileName;
        }
        else
        {
            return null;
        }
    }
    /**
     * 删除图片文件
     * 
     * @param path
     */
    public static boolean deletePicture(String path)
    {
        try
        {
            File file = new File(path);
            if (file.exists())
            {
                file.delete();
                return true;
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
