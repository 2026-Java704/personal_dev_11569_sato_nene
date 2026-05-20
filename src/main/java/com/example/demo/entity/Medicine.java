package com.example.demo.entity;

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
	private String count; // 服薬回数
	private String m_check; // 服薬したかどうか

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

	public Medicine(String name, String note, String count, String m_check) {
		this.name = name;
		this.note = note;
		this.count = count;
		this.m_check = m_check;
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

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getM_check() {
		return m_check;
	}

	public void setM_check(String m_check) {
		this.m_check = m_check;
	}

}