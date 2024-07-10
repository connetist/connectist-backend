package org.example.boardservice.board.controller;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.dto.request.lab.LabAddStarRequest;
import org.example.boardservice.board.dto.request.lab.LabRemoveStarRequest;
import org.example.boardservice.board.dto.request.lab.LabUpdateStarRequest;
import org.example.boardservice.board.dto.response.RestResponse;
import org.example.boardservice.board.service.lab.LabService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class LabController {


    private LabService labService;

    @Builder
    public LabController(LabService labService) {
        this.labService = labService;
    }
    // 특정 학교 전체 연구실 조회

    @GetMapping("/labs/{school}/{major}")
    public ResponseEntity<RestResponse<List<Lab>>> getAllLab(@PathVariable String school, @PathVariable String major) {
        List<Lab> labs = labService.findAllBySchoolAndMajor(school,major);
        RestResponse<List<Lab>> response = RestResponse.success(labs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 특정 연구실 조회
    @GetMapping("/lab/{labId}")
    public ResponseEntity<RestResponse<Lab>> getLab(@PathVariable String labId) {
        Lab lab = labService.findById(labId);
        RestResponse<Lab> response = RestResponse.success(lab);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 연구실 평점 매기기
    @PostMapping("/lab/star")
    public ResponseEntity<RestResponse<Lab>> addStar(@RequestBody LabAddStarRequest rq) {
        Lab lab = labService.addStar(rq);
        RestResponse<Lab> response = RestResponse.success(lab);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    // 연구실 평점 지우기
    @DeleteMapping("/lab/star")
    public ResponseEntity<RestResponse<Lab>> removeStar(@RequestBody LabRemoveStarRequest rq) {
        Lab lab = labService.removeStar(rq);
        RestResponse<Lab> response = RestResponse.success(lab);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 연구실 평점 변경
    @PatchMapping("/lab/star")
    public ResponseEntity<RestResponse<Lab>> updateStar(@RequestBody LabUpdateStarRequest rq) {
        Lab lab = labService.updateStar(rq);
        RestResponse<Lab> response = RestResponse.success(lab);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
