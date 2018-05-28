package com.example.shop.server;

import com.example.shop.entity.File;

public interface FileService {

    /**
     * 修改or保存图片
     * @param file
     */
    public void saveFile(File file);

    /**
     * 查询File通过File的id
     * @param file
     * @return
     */
    public File findFileById(File file);
}
