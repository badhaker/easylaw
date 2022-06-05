package com.vedalegal.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vedalegal.entity.RoleEntity;
import com.vedalegal.entity.UserEntity;
import com.vedalegal.enums.ApprovalStatus;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmail(String email);
	
	
	//UserEntity findByEmail(String email);
	//UserEntity findByMobileNo(String contactNo);
	//UserEntity findByCouncilNo(String BarCouncilNo);
	

   // public List<UserEntity> findAllByRole(RoleEntity roleEntity);
	
	@Query(value = "SELECT DISTINCT u.* FROM user u JOIN lawyer_expertise l ON u.id =l.lawyer_id WHERE  (?1 IS NULL OR u.state LIKE ?1) "
			+ "AND (?2 IS NULL OR u.city LIKE ?2) "
			+ "AND (?3 IS NULL OR u.expertise LIKE %?3%) "
			+ "AND (?4 IS NULL OR u.name LIKE %?4%) "
			+ "AND (?5 IS NULL OR u.lawyer_years_of_experience >= ?5) "
			+ "AND (?6 IS NULL OR u.gender= ?6)"
			+ "AND (u.is_active !=false)"
			+ "AND (u.is_suspend !=true)"
			+ "AND (l.is_active !=false)"
			+ "AND  u.approval_status = 'Approved' ", nativeQuery = true)
	List<UserEntity> findAllByLocationAndExpertise(String state, String city, String expertise, String name,
			Long lawyer_years_of_experience, String gender);
	
	List<UserEntity> findByApprovalStatusAndRole(ApprovalStatus status,RoleEntity role);
   
	List<UserEntity> findAllByRole(RoleEntity role);

	@Query("SELECT COUNT(u)>0 FROM UserEntity u WHERE u.contactNo=:contactNo AND active=true")
	Boolean checkMobileExist(Long contactNo);

	@Query("SELECT u FROM UserEntity u WHERE u.contactNo=:contactNo")
	UserEntity checkMobileExists(Long contactNo);

//	@Query(value="SELECT * FROM user u JOIN lawyer_expertise l ON u.id =l.lawyer_id WHERE  u.state LIKE ?1 AND u.city LIKE ?2 AND l.expertise_id= ?3 AND l.is_active!=false ", 
//			nativeQuery = true)
//	List<UserEntity> findAllByLocationAndExpertise(String state,String city,Long expertise);


	@Query("SELECT COUNT(u) FROM UserEntity u WHERE u.role=:userRole AND u.created>=:utilDate AND active=true")
	Long getCountByRoleAndCreatedDate(RoleEntity userRole, Date utilDate);

	@Query("SELECT u FROM UserEntity u WHERE u.role=?1 AND CONCAT(u.name, ' ', u.email,' ',u.contactNo) LIKE %?2%")
	List<UserEntity> findAllByRoleAndSearchValue(RoleEntity userRole, String search);

//	@Query("SELECT u FROM UserEntity u WHERE ( u.approvalStatus=?1 AND u.role=?2 AND CONCAT(u.name, ' ', u.email,' ',u.contactNo,' ',u.remarks) LIKE %?3%) OR( u.approvalStatus=?1 AND (u.city LIKE %?3% OR u.state LIKE %?3%))")
	@Query("SELECT u FROM UserEntity u WHERE  u.approvalStatus=?1 AND u.role=?2 AND CONCAT(u.name,' ', u.email,' ',u.contactNo,' ',COALESCE(u.remarks,''),' ',COALESCE(u.city,''),' ',COALESCE(u.state,' ')) LIKE %?3%")
	List<UserEntity> findByApprovalStatusAndRoleAndSearchValue(ApprovalStatus status, RoleEntity role, String search);
   
	Boolean existsByEmail(String email);
	
	Boolean existsBycontactNo(Long contactNo);
	
	  @Transactional
	  @Modifying
	  @Query("update UserEntity o set o.averageRating=:averageRating  where o.id =:lawyerId")
	  public void findByLawyerUpdate(@Param("lawyerId") Long lawyerId, @Param("averageRating") Long averageRating);


	  @Query("Select u from UserEntity u where u.email=:email and u.contactNo=:mobileNumber")
	  public UserEntity checkIfExists(String email,Long mobileNumber);

	  @Query("Select u from UserEntity u where u.role.id=:role_id AND u.active=true")
	  List<UserEntity> findAllByRoleId(Long role_id);

	  @Query("SELECT u FROM UserEntity u WHERE u.email=:email AND active=true")
	  UserEntity checkEmailExist(String email);
}
