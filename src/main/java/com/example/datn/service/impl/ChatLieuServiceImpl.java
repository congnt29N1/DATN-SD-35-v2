package com.example.datn.service.impl;

import com.example.datn.entity.ChatLieu;
import com.example.datn.exception.ChatLieuNotFoundException;
import com.example.datn.repository.ChatLieuRepository;
import com.example.datn.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {
    @Autowired
    ChatLieuRepository chatLieuRepository;
    @Override
    public List<ChatLieu> getAllChatLieu() {
        return chatLieuRepository.findAll();
    }

    @Override
    public List<ChatLieu> getAllPaginationChatLieu() {
        return chatLieuRepository.findAll(Sort.by("tenChatLieu").ascending());
    }

    @Override
    public Page<ChatLieu> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 , MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return chatLieuRepository.findAll(keyword,pageable);
        }
        return chatLieuRepository.findAll(pageable);
    }

    @Override
    public ChatLieu save(ChatLieu chatLieu) {
        return chatLieuRepository.save(chatLieu);
    }

    @Override
    public ChatLieu get(Integer id) throws ChatLieuNotFoundException, Exception {
        try {
            return chatLieuRepository.findById(id)
                    .orElseThrow(() -> new ChatLieuNotFoundException("Không tìm thấy vật liệu nào theo ID: " + id));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage()); // Xử lý ngoại lệ bằng cách throws Exception
        }
    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        ChatLieu chatLieuTheoTen = chatLieuRepository.findByTenChatLieu(ten);
        if (chatLieuTheoTen == null) return true;
        boolean isCreatingNew = (id == null);


        if (isCreatingNew) {
            if (chatLieuTheoTen != null) {
                return false;
            }
        }else {
            if (chatLieuTheoTen.getIdChatLieu() != id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateChatLieuEnabledStatus(Integer id, boolean enabled) {
        chatLieuRepository.updateEnabledStatus(id,enabled);
    }
}
