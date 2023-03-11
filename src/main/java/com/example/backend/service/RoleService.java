package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


public interface RoleService {
    boolean save();
    boolean saveAll();
    boolean delete();
}
