package com.example.app.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * DTO for storing a user's credentials.
 */
@ApiModel(description = "User Login Data")
@ToString
public class LoginForm {

   @NotNull
   @Pattern(regexp="^[._0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$")
   @Size(min = 4, max = 100)
   private String id;

   @NotNull
   @Size(min = 4, max = 100)
   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{12,}$")
   private String password;

   @ApiModelProperty(access = "public", required = true, notes="^[._0-9a-zA-Z-]+@[0-9a-zA-Z]+(.[_0-9a-zA-Z-]+)*$", position = 0)
   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   @ApiModelProperty(access = "public", required = true, notes="^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{12,}$", position = 1)
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}
