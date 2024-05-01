package cps.Controllers.DTO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TimeStampsDTO {
    private Timestamp[] timestamps;
}
