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

    /**
     * 删除图片通过图片的id
     * @param id 图片id
     */
    public void deleteFileById(Integer id);
}
