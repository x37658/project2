package com.iii.eeit124.adopt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.iii.eeit124.adopt.service.AdoptionRecordsService;
import com.iii.eeit124.adopt.service.AnimalsService;
import com.iii.eeit124.adopt.service.EmailService;
import com.iii.eeit124.entity.AdoptionRecords;
import com.iii.eeit124.entity.Animals;
import com.iii.eeit124.entity.Members;

@Controller
@RequestMapping("/adopt")
@SessionAttributes(names = { "adoptionRecord" })
public class AdoptController {

	@Autowired
	HttpSession session;
	@Autowired
	public AnimalsService animalsService;
	@Autowired
	public AdoptionRecordsService adoptionRecordsService;
	@Autowired
	public EmailService emailService;

	// detail轉至notice
	@GetMapping("/adoptNotice/{animalId}")
	public String processAdoptNotice(@PathVariable("animalId") Integer animalsId, Model m) {

		// 讀會員entity
		Members member = (Members) session.getAttribute("LoginOK");
		//讀ownerMember
		Members ownerMember = animalsService.read(animalsId).getMember();
		// 讀領養紀錄list
		List<AdoptionRecords> adoptionRecordList = adoptionRecordsService.read(member.getId(), animalsId);
		if (adoptionRecordList.isEmpty()) {
			// 若無領養紀錄list，則新增一個
			AdoptionRecords adoptionRecords = new AdoptionRecords();
			adoptionRecords.setMember(member);
			adoptionRecords.setOwnerMember(ownerMember);
			adoptionRecords.setAnimal(animalsService.read(animalsId));
			adoptionRecords.setNoticeOptions(0);
			adoptionRecords.setCreatedAt(new Date());
			adoptionRecordsService.create(adoptionRecords);
			m.addAttribute("adoptionRecord", adoptionRecords);
			session.setAttribute("myAdoptionRecord", adoptionRecords);
		} else {
			// 若有領養紀錄list
			for (AdoptionRecords adoptionRecord : adoptionRecordList) {
				// 注意事項陣列
				int[] noticeArray;
				noticeArray = new int[10];
				for (int i = 9; i > -1; i--) {
					if (adoptionRecord.getNoticeOptions() > Math.pow(2, i)) {
						noticeArray[i] = 1;
					} else {
						noticeArray[i] = 0;
					}
					System.out.println("noticeArray[" + i + "]=" + noticeArray[i]);
				}

				m.addAttribute("noticeArray", noticeArray);
				// 存在的領養紀錄加入Attribute
				m.addAttribute("adoptionRecord", adoptionRecord);
				session.setAttribute("myAdoptionRecord", adoptionRecord);
			}
		}

		// 顯示今天日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		m.addAttribute("Today", sdf.format(new Date()));
		return "adopt/AdoptNotice";
	}

	// notice轉至apply
	@PostMapping("/adoptApply")
	public String processAdoptApply(@ModelAttribute("adoptionRecord") AdoptionRecords adoptionRecords, Model m,
			@RequestParam("notice1") Integer notice1, @RequestParam("notice2") Integer notice2,
			@RequestParam("notice3") Integer notice3, @RequestParam("notice4") Integer notice4,
			@RequestParam("notice5") Integer notice5, @RequestParam("notice6") Integer notice6,
			@RequestParam("notice7") Integer notice7, @RequestParam("notice8") Integer notice8,
			@RequestParam("notice9") Integer notice9, @RequestParam("notice10") Integer notice10
//			,@RequestParam("animalId") Integer animalsId
	) {
		// 將注意事項選項二進位轉成十進位
		int[] notice = { notice1, notice2, notice3, notice4, notice5, notice6, notice7, notice8, notice9, notice10 };
		Integer sum = 0;
		for (int i = 0; i < notice.length; i++) {
			sum = (int) (sum + notice[i] * Math.pow(2, i));
		}

		// 賦予adoptionRecords值
		adoptionRecords.setMember((Members) session.getAttribute("LoginOK"));// 更新還是要重設一次
		adoptionRecords.setAnimal(animalsService.read(adoptionRecords.getAnimalId()));
		adoptionRecords.setNoticeOptions(sum);
		adoptionRecords.setUpdatedAt(new Date());
		adoptionRecordsService.update(adoptionRecords);

		m.addAttribute("adoptionRecord", adoptionRecords);
		session.setAttribute("myAdoptionRecord", adoptionRecords);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		m.addAttribute("Today", sdf.format(new Date()));
		return "adopt/AdoptApply";
	}

	// Refresh
	@GetMapping("/adoptApply")
	public String processAdoptApplyRefresh(Model m) {
		m.addAttribute("adoptionRecord",
				(adoptionRecordsService.read(((Members) session.getAttribute("LoginOK")).getId(),
						((AdoptionRecords) session.getAttribute("myAdoptionRecord")).getAnimalId())).get(0));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		m.addAttribute("Today", sdf.format(new Date()));
		return "adopt/AdoptApply";
	}

	// apply送出
	@PostMapping("/apply")
	public String processApply(@ModelAttribute("adoptionRecord") AdoptionRecords adoptionRecords,
//			@RequestParam("animalId") Integer animalsId,
			Model m) throws ParseException {
		Members member = (Members) session.getAttribute("LoginOK");
		Animals animals = animalsService.read(adoptionRecords.getAnimalId());

		if (null == adoptionRecords.getAgreement()) {
			adoptionRecords.setAgreement(0);
		}
		adoptionRecords.setMember(member);// 更新還是要重設一次
		adoptionRecords.setAnimal(animals);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if ("" == adoptionRecords.getBirthdayString()) {
			adoptionRecords.setBirthday(new Date());
		} else {
			Date birthday = sdf.parse(adoptionRecords.getBirthdayString());
			adoptionRecords.setBirthday(birthday);
		}
		adoptionRecords.setApplyTime(new Date());
		
		//設定審核狀態
		adoptionRecords.setReviewStatus(1);
		
		//將新增的資料更新進領養紀錄
		adoptionRecords.setUpdatedAt(new Date());
		adoptionRecordsService.update(adoptionRecords);
		session.setAttribute("myAdoptionRecord", adoptionRecords);
		
		//信件內容
//		String content=null;
//		content = "";
		
		//寄mail
//		emailService.sendSimpleMessage(adoptionRecords.getEmail(), "有人向您的寵物提出領養申請", content);

		return "redirect:/MemberCenter/adoptionRequestList.controller?source=MyAdoptionProgress";
	}
	
	// Refresh
	@GetMapping("/apply")
	public String processAdoptRefresh(Model m) {
		m.addAttribute("AnimalsList", animalsService.readAll());
		m.addAttribute("source", "AdoptAnimal");
		return "adopt/ReadAnimal";
	}
}