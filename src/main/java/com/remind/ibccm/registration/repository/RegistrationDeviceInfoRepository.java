package com.remind.ibccm.registration.repository;

import com.remind.ibccm.registration.entity.RegistrationInfo;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationDeviceInfoRepository extends CrudRepository<RegistrationInfo, Integer> {

    List<RegistrationInfo> findByRemindTypeAndRemindUuidOrderByUpdatedAtDesc(String remindType, String remindUuid);

    List<RegistrationInfo> findByStatusOrderByUpdatedAtDesc(String name);

    @Query(value = "update registration_info set status = 'i' where updated_at < :expectedTime and status = 'a'")
    @Modifying
    int batchUpdate(LocalDateTime expectedTime);

    List<RegistrationInfo> findByRemindTypeAndStatusOrderByUpdatedAtDesc(String remindType, String status);

    @Query(value = "delete from registration_info where status in ('i', 'e') and updated_at < :expectedTime ")
    @Modifying
    int batchHouseKeeping(LocalDateTime expectedTime);
}
