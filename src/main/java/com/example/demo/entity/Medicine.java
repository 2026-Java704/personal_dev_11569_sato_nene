package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicine")
public class Medicine {

	//	フィールド

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 薬ID

	private String name; // 薬名
	private String note; // 薬情報
	private Integer count; // 個数
	private String medicineType; // 処方薬か市販薬か
	private String timing; // 朝・昼・夜
	private String mealTiming; // 食前・食後
	@Column(name = "m_check")
	private Boolean checked; // 服薬したかどうか
	private LocalDate date; // 飲み始め日
	private LocalTime time; // 飲んだ時間

	//	多対一の関係
	@ManyToOne
	@JoinColumn(name = "users_id")

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Medicine(String name, String note, Integer count, String medicineType, String timing, String mealTiming,
			Boolean m_check, User user, LocalDate date, LocalTime time) {
		this.name = name;
		this.note = note;
		this.count = count;
		this.medicineType = medicineType;
		this.timing = timing;
		this.mealTiming = mealTiming;
		this.checked = m_check;
		this.user = user;
		this.date = date;
		this.time = time;
	}

	public Medicine() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMedicineType() {
		return medicineType;
	}

	public void setMedicineType(String medicineType) {
		this.medicineType = medicineType;
	}

	public String getTiming() {
		return timing;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public String getMealTiming() {
		return mealTiming;
	}

	public void setMealTiming(String mealTiming) {
		this.mealTiming = mealTiming;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

}
