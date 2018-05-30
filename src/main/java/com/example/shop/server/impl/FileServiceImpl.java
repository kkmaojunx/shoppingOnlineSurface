package com.example.shop.server.impl;

import com.example.shop.entity.File;
import com.example.shop.repository.FileRepository;
import com.example.shop.server.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 图片
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Resource
    private FileRepository fileRepository;

    @Override
    public void saveFile(File file) {
        fileRepository.save(file);
    }

    @Override
    public File findFileById(File file) {
        return fileRepository.getOne(file.getId());
    }

    @Override
    public void deleteFileById(Integer id) {
        fileRepository.deleteById(id);
    }
}
