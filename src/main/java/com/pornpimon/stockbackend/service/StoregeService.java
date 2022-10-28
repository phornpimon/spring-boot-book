package com.pornpimon.stockbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface StoregeService {
    // public void init();
    public String store(MultipartFile file);
}
