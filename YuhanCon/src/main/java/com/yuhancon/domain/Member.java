package com.yuhancon.domain;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, unique = true)
	
	@NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식을 지켜주세요.")
	private String email;
	@Column(nullable=false)
	
	@NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "비밀번호는 영어+숫자 조합 8자 이상이어야 합니다.")
	private String password;
	
	@NotBlank(message = "이름은 필수입니다.")
	@Column(nullable=false)
	private String name;
	
	@NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "전화번호는 '-' 없이 숫자만 10~11자리 입력하세요.")
	@Column(nullable=false)
	private String phone;
	
	
	@Column(nullable=false)
	private String role = "USER";

}
