package com.example.app.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "User Sign Up Data")
@ToString
public class SignUpForm {

   @NotNull
   @Size(min = 1, max = 50)
   private String name;

   @NotNull
   @Size(min = 12, max = 100)
   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{12,}$")
   private String password;

   @NotNull
   @Pattern(regexp="^[._0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
   @Size(min = 4, max = 100)
   private String id;

   @ApiModelProperty(access = "public", required = true, notes="1-50자")
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
   @ApiModelProperty(access = "public", required = true, notes = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{12,}$")
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @ApiModelProperty(access = "public", required = true, notes = "^[._0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

}
