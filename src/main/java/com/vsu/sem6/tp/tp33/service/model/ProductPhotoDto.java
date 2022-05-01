package com.vsu.sem6.tp.tp33.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = { "content","photo" })
public class ProductPhotoDto {
    private UUID id;

    private String photo;

    private MultipartFile content;
    private UUID productId;
}
