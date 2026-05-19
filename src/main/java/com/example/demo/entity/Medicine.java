package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {

	//	フィールド

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medicine_id")
	private Integer medicineId; // 薬ID

	@Column(name = "medicine_name")
	private String medicineName; // 薬名

	@Column(name = "medicine_note")
	private String medicineNote; // 薬情報

	@Column(name = "medicine_count")
	private String medicineCount; // 服薬回数

	@Column(name = "medicine_check")
	private String medicineCheck; // 服薬したかどうか

	//	ゲッター

	public Integer getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Integer medicineId) {
		this.medicineId = medicineId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public String getMedicineNote() {
		return medicineNote;
	}

	public void setMedicineNote(String medicineNote) {
		this.medicineNote = medicineNote;
	}

	public String getMedicineCount() {
		return medicineCount;
	}

	public void setMedicineCount(String medicineCount) {
		this.medicineCount = medicineCount;
	}

	public String getMedicineCheck() {
		return medicineCheck;
	}

	public void setMedicineCheck(String medicineCheck) {
		this.medicineCheck = medicineCheck;
	}

}