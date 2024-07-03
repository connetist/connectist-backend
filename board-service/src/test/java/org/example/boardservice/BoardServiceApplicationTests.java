package org.example.boardservice;

import org.example.boardservice.board.domain.Lab;
import org.example.boardservice.board.domain.Star;
import org.example.boardservice.board.service.LabService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Arrays;

@SpringBootTest
@SqlGroup(
		@Sql(value = "/sql/data.sql")
)
class BoardServiceApplicationTests {

	@Autowired
	private LabService labService;
	@Test
	void contextLoads() {
	}

//	@Test
//	void getLabByIdTest(){
//		Lab lab = labService.findById("lab1");
//
//		System.out.println(lab.getLabId());
//		System.out.println(lab.getContents());
//		System.out.println(lab.getMajor());
//		System.out.println(lab.getSchool());
//		System.out.println(lab.getCreatedAt());
//		System.out.println(lab.getStars().size());
//
//	}
//
//	@Test
//	void saveStarInLab(){
//		Lab lab = labService.addStar("user10",5,"lab1");
//		System.out.println(lab.getStars().size());
//	}

}
