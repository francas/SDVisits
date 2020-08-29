package com.example.application.backend.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="visit")
public class Visit extends AbstractEntity implements Cloneable {

  @NotNull
  @ManyToOne
  @JoinColumn(name = "specialist_id")
  private Specialist specialist;

  @NotNull
  @NotEmpty
  private Date createdDate;

  @NotNull
  @NotEmpty
  private String code;

  private Date acceptedDate;

  private Date canceledDate;

  private Date completedDate;

  public Specialist getSpecialist() {
    return specialist;
  }

  public void setSpecialist(Specialist specialist) {
    this.specialist = specialist;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getAcceptedDate() {
    return acceptedDate;
  }

  public void setAcceptedDate(Date acceptedDate) {
    this.acceptedDate = acceptedDate;
  }

  public Date getCanceledDate() {
    return canceledDate;
  }

  public void setCanceledDate(Date canceledDate) {
    this.canceledDate = canceledDate;
  }

  public Date getCompletedDate() {
    return completedDate;
  }

  public void setCompletedDate(Date completedDate) {
    this.completedDate = completedDate;
  }

  @Override
  public String toString() {
    return code + " " + createdDate;
  }
}
