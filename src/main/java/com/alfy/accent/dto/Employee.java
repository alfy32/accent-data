package com.alfy.accent.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Alan Christensen
 * 12/23/2014
 */

@XmlRootElement
public class Employee {
  private String id;
  private String name;
  private String phoneNumber;
  private String address;

  public Employee() { }

  public Employee(String id, String name, String phoneNumber, String address) {
    this.id = id;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
