package com.example.aim.consultant.application;

import com.example.aim.consultant.application.dto.request.ConsultationRequestDto;

public interface ConsultationService {
    Long requestConsultation(ConsultationRequestDto requestDto,String username);
}
